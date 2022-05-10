package dk.sdu.se_f22.contentmodule.index.ui.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestAnimation {

    private static final String PAWN_URL = "https://pngset.com/images/icon-chess-pawn-chess-laptop-icon-with-and-vector-format-gray-world-of-warcraft-transparent-png-1911210.png";

    private Image pawn;

    private Map<Location, Pawn> pawnLocations = new HashMap<>();

    private Board board;

    private Timer timer;

    private JLayeredPane glassPane;

    public TestAnimation() {
        try {
            pawn = new ImageIcon(new URL(PAWN_URL)).getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static class Location {
        public final int row;
        public final int col;

        public Location(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + col;
            result = prime * result + row;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Location other = (Location) obj;
            return (col == other.col && row == other.row);
        }
    }

    private static class Cell extends JPanel {

        private final Location location;

        public Cell(Location location) {
            super(new BorderLayout());
            this.location = location;
            setOpaque(true);
            setBackground(((location.row + location.col) % 2) == 0 ? Color.WHITE : Color.BLACK);
        }

        @Override
        protected void addImpl(Component comp, Object constraints, int index) {
            while (getComponentCount() > 0) {
                remove(0);
            }
            super.addImpl(comp, constraints, index);
        }
    }

    private static class Board extends JPanel {

        private Map<Location, Cell> cells = new HashMap<>();

        public Board() {
            super(new GridLayout(8, 8));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Cell cell = new Cell(new Location(i, j));
                    add(cell);
                    cells.put(new Location(i, j), cell);
                }
            }
        }

        public void add(Pawn pawn, Location location) {
            cells.get(location).add(pawn);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        public Cell getCell(Location location) {
            return cells.get(location);
        }
    }

    private class Pawn extends JComponent {
        public Pawn() {
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(pawn, 0, 0, getWidth(), getHeight(), this);
        }
    }

    protected void initUI() {
        JFrame frame = new JFrame(TestAnimation.class.getSimpleName());
        board = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                Location location = new Location(i, j);
                Pawn aPawn = new Pawn();
                board.add(aPawn, location);
                pawnLocations.put(location, aPawn);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 6; j < 8; j++) {
                Location location = new Location(i, j);
                Pawn aPawn = new Pawn();
                board.add(aPawn, location);
                pawnLocations.put(location, aPawn);
            }
        }
        timer = new Timer(7000, new Animation());
        timer.setInitialDelay(0);
        timer.setRepeats(true);
        timer.setCoalesce(false);
        glassPane = new JLayeredPane();
        glassPane.setOpaque(false);
        frame.add(board);
        frame.setGlassPane(glassPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        timer.start();
        glassPane.setVisible(true);
    }

    public class Animation implements ActionListener {

        private Map<Location, Pawn> futureLocations;

        private Random random = new Random();

        private Timer subTimer;

        private List<Pawn> movingPawns;

        private Map<Pawn, Point> originalCoordinates = new HashMap<>();
        private Map<Pawn, Point> futureCoordinates = new HashMap<>();

        @Override
        public void actionPerformed(ActionEvent e) {
            futureLocations = new HashMap<>();
            movingPawns = new ArrayList<>();
            for (Pawn p : pawnLocations.values()) {
                int row = random.nextInt(8);
                int col = random.nextInt(8);
                Location location;
                while (futureLocations.containsKey((location = new Location(row, col)))) {
                    row = random.nextInt(8);
                    col = random.nextInt(8);
                }
                futureLocations.put(location, p);
                Cell futureCell = board.getCell(location);
                futureCoordinates.put(p, SwingUtilities.convertPoint(futureCell, 0, 0, glassPane));
                movingPawns.add(p);
            }
            for (Pawn p : movingPawns) {
                Point locationInGlassPane = SwingUtilities.convertPoint(p.getParent(), 0, 0, glassPane);
                glassPane.add(p);
                p.setLocation(locationInGlassPane);
                originalCoordinates.put(p, locationInGlassPane);
            }
            subTimer = new Timer(50, new AnimationSteps());
            subTimer.setInitialDelay(0);
            subTimer.setCoalesce(true);
            subTimer.setRepeats(true);
            subTimer.start();
        }

        public class AnimationSteps implements ActionListener {

            private int step = 0;

            @Override
            public void actionPerformed(ActionEvent e1) {
                if (step < 50 + 1) {
                    for (Pawn p : movingPawns) {
                        Point p1 = originalCoordinates.get(p);
                        Point p2 = futureCoordinates.get(p);
                        int x = (int) (p1.x + ((p2.x - p1.x) * (double) step / 50));
                        int y = (int) (p1.y + ((p2.y - p1.y) * (double) step / 50));
                        p.setLocation(x, y);
                    }
                } else {
                    for (Entry<Location, Pawn> e : futureLocations.entrySet()) {
                        board.add(e.getValue(), e.getKey());
                    }
                    board.revalidate();
                    subTimer.stop();
                    pawnLocations = futureLocations;
                }
                step++;

            }

        }
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TestAnimation().initUI();
            }
        });
    }
}