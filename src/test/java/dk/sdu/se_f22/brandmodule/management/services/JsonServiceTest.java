package dk.sdu.se_f22.brandmodule.management.services;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    @Test
    void deserializeBrand() {

        Brand known = new Brand();
        known.name = "Acer";
        known.description = "Acer Inc. is a Taiwanese multinational hardware and electronics corporation specializing in advanced electronics technology, headquartered in Xizhi, New Taipei City. Its products include desktop PCs, laptop PCs (clamshells, 2-in-1s, convertibles and Chromebooks), tablets, servers, storage devices, virtual reality devices, displays, smartphones and peripherals, as well as gaming PCs and accessories under its Predator brand. Acer is the world's 6th-largest PC vendor by unit sales as of January 2021.";
        known.founded = "1 August 1976";
        known.headquarters = "Xizhi, New Taipei, Taiwan";
        known.products = new ArrayList<>();
        String[] allProductsFromAcer = {"personal computers",
                    "smartphones",
                    "servers",
                    "storage",
                    "handhelds",
                    "monitors",
                    "LEDs",
                    "LCDs",
                    "video projectors",
                    "e-business"};
        known.products.addAll(List.of(allProductsFromAcer));


        JsonService s = new JsonService();

        System.out.println("test Acer");

        int indexNumber = 0;
        assertEquals(known.name,s.deserializeBrand().get(indexNumber).name);
        assertEquals(known.description,s.deserializeBrand().get(indexNumber).description);
        assertEquals(known.founded,s.deserializeBrand().get(indexNumber).founded);
        assertEquals(known.headquarters,s.deserializeBrand().get(indexNumber).headquarters);
        for(int i =0; i<known.products.size();i++){
            assertEquals(known.products.get(i),s.deserializeBrand().get(indexNumber).products.get(i));
            //  System.out.println(s.deserializeBrand().get(i).name);
        }


        known.name = "Intel";
        known.description = "Intel Corporation, stylized as intel, is an American multinational corporation and technology company headquartered in Santa Clara, California. It is the world's largest semiconductor chip manufacturer by revenue, and is the developer of the x86 series of microprocessors, the processors found in most personal computers (PCs). Incorporated in Delaware, Intel ranked No. 45 in the 2020 Fortune 500 list of the largest United States corporations by total revenue for nearly a decade, from 2007 to 2016 fiscal years. Intel supplies microprocessors for computer system manufacturers such as Acer, Lenovo, HP, and Dell. Intel also manufactures motherboard chipsets, network interface controllers and integrated circuits, flash memory, graphics chips, embedded processors and other devices related to communications and computing.";
        known.founded = "July 18, 1968";
        known.headquarters = "San Francisco California, U.S.";
        known.products = new ArrayList<>();
        String[] allProducts = {"Central processing units",
                "Microprocessors",
                "Integrated graphics processing units (iGPU)",
                "Systems-on-chip (SoCs)",
                "Motherboard chipsets",
                "Network interface controllers",
                "Modems",
                "Mobile phones",
                "Solid state drives",
                "Wi-Fi and Bluetooth Chipsets",
                "Flash memory",
                "Vehicle automation sensors"};
        known.products.addAll(List.of(allProducts));

        System.out.println("test Intel");
        indexNumber = 6;
        assertEquals(known.name,s.deserializeBrand().get(indexNumber).name);
        assertEquals(known.description,s.deserializeBrand().get(indexNumber).description);
        assertEquals(known.founded,s.deserializeBrand().get(indexNumber).founded);
        assertEquals(known.headquarters,s.deserializeBrand().get(indexNumber).headquarters);
        for(int i =0; i<known.products.size();i++){
            assertEquals(known.products.get(i),s.deserializeBrand().get(indexNumber).products.get(i));
            //  System.out.println(s.deserializeBrand().get(i).name);
        }
    }
}
