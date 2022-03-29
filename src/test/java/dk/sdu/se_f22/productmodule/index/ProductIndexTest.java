package dk.sdu.se_f22.productmodule.index;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductIndexTest {

    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll executed");
    }

    @AfterAll
    static void tearDown(){
        System.out.println("@AfterAll executed");
    }

}