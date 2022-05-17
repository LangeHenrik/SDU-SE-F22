package dk.sdu.se_f22.sortingmodule;

import dk.sdu.se_f22.sharedlibrary.models.Brand;
import dk.sdu.se_f22.sharedlibrary.models.Content;
import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.time.Instant;
import java.util.*;

public class MainWithMockData {

    private static Collection<Brand> brands = new ArrayList<Brand>();
    private static Collection<Content> contents = new ArrayList<Content>();
    private static Collection<Product> products = new ArrayList<Product>();

    public static void main(String[] args) {

        ArrayList<String> acer_products = new ArrayList<>();
        acer_products.add("personal computers");
        acer_products.add("smartphones");
        acer_products.add("servers");
        acer_products.add("storage");
        acer_products.add("handhelds");
        acer_products.add("monitors");
        acer_products.add("LEDs");
        acer_products.add("LCDs");
        acer_products.add("video projectors");
        acer_products.add("e-business");

        Brand acer_brand = new Brand("Acer", "Acer Inc. is a Taiwanese multinational hardware and electronics corporation specializing in advanced electronics technology, headquartered in Xizhi, New Taipei City. Its products include desktop PCs, laptop PCs (clamshells, 2-in-1s, convertibles and Chromebooks), tablets, servers, storage devices, virtual reality devices, displays, smartphones and peripherals, as well as gaming PCs and accessories under its Predator brand. Acer is the world's 6th-largest PC vendor by unit sales as of January 2021.", "1 August 1976", "Xizhi, New Taipei, Taiwan", acer_products);
        brands.add(acer_brand);

        ArrayList<String> hp_products = new ArrayList<>();
        hp_products.add("Personal computers");
        hp_products.add("printers");
        hp_products.add("digital press");
        hp_products.add("3D printers");
        hp_products.add("scanners");
        hp_products.add("copiers");
        hp_products.add("monitors");

        Brand hp_brand = new Brand("HP","HP Inc. is an American multinational information technology company headquartered in Palo Alto, California, that develops personal computers (PCs), printers and related supplies, as well as 3D printing solutions.", "January 1, 1939", "Palo Alto, California, United States", hp_products);
        brands.add(hp_brand);

        ArrayList<String> dell_products = new ArrayList<>();
        dell_products.add("Personal computers");
        dell_products.add("Servers");
        dell_products.add("Peripherals");
        dell_products.add("Smartphones");
        dell_products.add("Televisions");

        Brand dell_brand = new Brand("Dell", "Dell is an American company that develops, sells, repairs, and supports computers and related products and services, and is owned by its parent company of Dell Technologies. Founded in 1984 by Michael Dell, the company is one of the largest technology corporations in the world, employing more than 165,000 people in the United States (US) and around the world. Dell sells personal computers (PCs), servers, data storage devices, network switches, software, computer peripherals, HDTVs, cameras, printers, and electronics built by other manufacturers. The company is well known for its innovations in supply chain management and electronic commerce, particularly its direct-sales model and its \"build-to-order\" or \"configure to order\" approach to manufacturing—delivering individual PCs configured to customer specifications. Dell was a pure hardware vendor for much of its existence, but with the acquisition in 2009 of Perot Systems, Dell entered the market for IT services. The company has since made additional acquisitions in storage and networking systems, with the aim of expanding their portfolio from offering computers only to delivering complete solutions for enterprise customers.", "February 1, 1984","Round Rock, Texas, US", dell_products);
        brands.add(dell_brand);

        ArrayList<String> samsung_products = new ArrayList<>();
        samsung_products.add("Clothing");
        samsung_products.add("automotive");
        samsung_products.add("chemicals");
        samsung_products.add("consumer electronics");
        samsung_products.add("electronic components");
        samsung_products.add("medical equipment");
        samsung_products.add("semiconductors");
        samsung_products.add("solid state drives");
        samsung_products.add("DRAM");
        samsung_products.add("flash memory");
        samsung_products.add("ships");
        samsung_products.add("telecommunications equipment");
        samsung_products.add("home appliance");

        Brand samsung_brand = new Brand("Samsung", "The Samsung Group (or simply Samsung, stylized in logo as SΛMSUNG) is a South Korean multinational manufacturing conglomerate headquartered in Samsung Town, Seoul, South Korea. It comprises numerous affiliated businesses, most of them united under the Samsung brand, and is the largest South Korean chaebol (business conglomerate). As of 2020, Samsung has the 8th highest global brand value.", "1 March 1938","Seocho District, Seoul, South Korea", samsung_products);
        brands.add(samsung_brand);

        ArrayList<String> intel_products = new ArrayList<>();
        intel_products.add("Central processing units");
        intel_products.add("Microprocessors");
        intel_products.add("Integrated graphics processing units (iGPU) ");
        intel_products.add("Systems-on-chip (SoCs)");
        intel_products.add("Motherboard chipsets");
        intel_products.add("Network interface controllers");
        intel_products.add("Modems");
        intel_products.add("Mobile phones");
        intel_products.add("Solid state drives");
        intel_products.add("Wi-Fi and Bluetooth Chipsets");
        intel_products.add("Flash memory");
        intel_products.add("Vehicle automation sensors");

        Brand intel_brand = new Brand("Intel", "Intel Corporation, stylized as intel, is an American multinational corporation and technology company headquartered in Santa Clara, California. It is the world's largest semiconductor chip manufacturer by revenue, and is the developer of the x86 series of microprocessors, the processors found in most personal computers (PCs). Incorporated in Delaware, Intel ranked No. 45 in the 2020 Fortune 500 list of the largest United States corporations by total revenue for nearly a decade, from 2007 to 2016 fiscal years. Intel supplies microprocessors for computer system manufacturers such as Acer, Lenovo, HP, and Dell. Intel also manufactures motherboard chipsets, network interface controllers and integrated circuits, flash memory, graphics chips, embedded processors and other devices related to communications and computing.", "July 18, 1968","San Francisco California, U.S.",intel_products);
        brands.add(intel_brand);

        ArrayList<String> lenovo_products = new ArrayList<>();
        lenovo_products.add("Personal computers");
        lenovo_products.add("smartphones");
        lenovo_products.add("servers");
        lenovo_products.add("supercomputers");
        lenovo_products.add("peripherals");
        lenovo_products.add("printers");
        lenovo_products.add("televisions");
        lenovo_products.add("scanners");
        lenovo_products.add("storage devices");

        Brand lenovo_brand = new Brand("Lenovo", "Lenovo Group Limited, often shortened to Lenovo, is a Chinese / American multinational technology company specializing in designing, manufacturing, and marketing consumer electronics, personal computers, software, business solutions, and related services. Products manufactured by the company include desktop computers, laptops, tablet computers, smartphones, workstations, servers, supercomputers, electronic storage devices, IT management software, and smart televisions. Its best-known brands include IBM's ThinkPad business line of laptop computers, the IdeaPad, Yoga, and Legion consumer lines of laptop computers, and the IdeaCentre and ThinkCentre lines of desktop computers. As of January 2021, Lenovo is the world's largest personal computer vendor by unit sales.", "1 November 1984", "Hong Kong", lenovo_products);
        brands.add(lenovo_brand);

        ArrayList<String> msi_products = new ArrayList<>();
        msi_products.add("Personal computers");
        msi_products.add("Motherboards");
        msi_products.add("Graphics cards");
        msi_products.add("PC components");
        msi_products.add("Peripherals");
        msi_products.add("Monitors");
        msi_products.add("Server hardware");

        Brand msi_brand = new Brand("MSI", "Micro-Star International Co., Ltd is a Taiwanese multinational information technology corporation headquartered in New Taipei City, Taiwan. It designs, develops and provides computer hardware, related products and services, including laptops, desktops, motherboards, graphics cards, All-in-One PCs, servers, industrial computers, PC peripherals, car infotainment products, etc.","4 August 1986","Zhonghe, New Taipei, Taiwan", msi_products);
        brands.add(msi_brand);

        /**
         * Products
         */

        Product tmp = new Product(UUID.fromString("1cf3d1fd-7787-4b64-8ef9-0b6f131a9f4e"),4.446,new ArrayList<String>(Arrays.asList("Charlottenlund","Herning")),2054647099864l,1787.50,Instant.parse("2021-06-02T05:05:06.622164"),Instant.parse("2025-01-25T07:40:33.169509"),"PC/Laptops","Lenovo ThinkPad T410 35.8 cm (14.1\")","Lenovo ThinkPad T410, 35.8 cm (14.1\"), 1280 x 800 pixels Lenovo ThinkPad T410. Display diagonal: 35.8 cm (14.1\"), Display resolution: 1280 x 800 pixels");
        products.add(tmp);
        tmp = new Product(UUID.fromString("ea6954c2-64ec-4a65-b1a5-d614907e8b65"),3.328,new ArrayList<String>(Arrays.asList("Esbjerg","Naestved","Odense","Arhus","Charlottenlund","Ballerup","Roskilde")),3623989743323l,795.50,Instant.parse("2021-01-19T23:22:04.313630"),Instant.parse("2022-06-29T04:29:05.547045"),"PC/Laptops","Lenovo ThinkPad Tablet Folio Case - Hebrew keyboard","Lenovo ThinkPad Tablet Keyboard Folio Case - Hebrew, 27.2 cm (10.7\"), 35 \u00b0C Lenovo ThinkPad Tablet Keyboard Folio Case - Hebrew. Height (imperial): 27.2 cm (10.7\"), Maximum operating temperature: 35 \u00b0C");
        products.add(tmp);
        tmp = new Product(UUID.fromString("73f5e31e-bcaa-4939-a4fd-4cded7ab9eb8"),4.834,new ArrayList<String>(Arrays.asList("Hvidovre","Silkeborg","Aalborg","Roskilde","Charlottenlund","Horsens","Odense","Herning","Randers","Ballerup","Vejle","Frederiksberg")),4606916223791l,1775.50,Instant.parse("2021-01-17T08:33:12.688783"),Instant.parse("2025-01-23T21:40:01.372553"),"PC/Laptops","Lenovo IdeaPad S300","Lenovo IdeaPad S300 Lenovo IdeaPad S300");
        products.add(tmp);
        tmp = new Product(UUID.fromString("45d6065e-eb97-48ce-afb3-310bd698f0fb"),2.955,new ArrayList<String>(Arrays.asList("Charlottenlund","Odense","Silkeborg","Aalborg","Vejle","Frederiksberg","Naestved","Greve")),2642511756618l,2325.00,Instant.parse("2014-10-18T07:27:25.685899"),Instant.parse("2026-03-09T02:00:29.107681"),"PC/Laptops","HP ProBook 445 G7 Notebook 35.6 cm (14\") Full HD AMD Ryzen 5 8 GB DDR4-SDRAM 512 GB SSD Wi-Fi 5 (802.11ac) Windows 10 Home Silver","HP ProBook 445 G7, AMD Ryzen 5, 2.3 GHz, 35.6 cm (14\"), 1920 x 1080 pixels, 8 GB, 512 GB HP ProBook 445 G7. Product type: Notebook, Form factor: Clamshell. Processor family: AMD Ryzen 5, Processor model: 4500U, Processor frequency: 2.3 GHz. Display diagonal: 35.6 cm (14\"), HD type: Full HD, Display resolution: 1920 x 1080 pixels. Internal memory: 8 GB, Internal memory type: DDR4-SDRAM. Total storage capacity: 512 GB, Storage media: SSD. On-board graphics adapter model: AMD Radeon Graphics. Operating system installed: Windows 10 Home. Product colour: Silver");
        products.add(tmp);
        tmp = new Product(UUID.fromString("afd16cbd-5469-47e0-ba93-4a045a732a71"),1.644,new ArrayList<String>(Arrays.asList("Esbjerg","Frederiksberg","Hvidovre","Horsens","Charlottenlund","Greve","Kolding","Odense","Roskilde","Silkeborg")),9012802468286l,1893.75,Instant.parse("2015-02-03T01:46:30.732537"),Instant.parse("2022-03-07T00:39:28.430317"),"PC/Laptops","HP Pavilion g6-1165sa Notebook 39.6 cm (15.6\") Intel\u00ae Pentium\u00ae 4 GB DDR3-SDRAM 750 GB HDD Windows 7 Home Premium Red","HP Pavilion g6-1165sa, Intel\u00ae Pentium\u00ae, 2 GHz, 39.6 cm (15.6\"), 1366 x 768 pixels, 4 GB, 750 GB HP Pavilion g6-1165sa. Product type: Notebook, Form factor: Clamshell. Processor family: Intel\u00ae Pentium\u00ae, Processor model: B940, Processor frequency: 2 GHz. Display diagonal: 39.6 cm (15.6\"), Display resolution: 1366 x 768 pixels. Internal memory: 4 GB, Internal memory type: DDR3-SDRAM. Total storage capacity: 750 GB, Storage media: HDD, Optical drive type: DVD Super Multi DL. On-board graphics adapter model: Intel\u00ae HD Graphics. Operating system installed: Windows 7 Home Premium. Product colour: Red");
        products.add(tmp);
        tmp = new Product(UUID.fromString("04a0a803-911e-48b7-8c64-0ebf8ad448cf"),3.315,new ArrayList<String>(Arrays.asList("Randers","Naestved","Frederiksberg","Odense")),7733947550217l,1099.75,Instant.parse("2019-06-17T12:55:36.073941"),Instant.parse("2025-11-19T23:09:29.511676"),"PC/Laptops","Sony SVF1521B1E","Sony SVF1521B1E Sony SVF1521B1E");
        products.add(tmp);
        tmp = new Product(UUID.fromString("48c7ec0d-d9f5-4c9d-b36a-901c11a2adb9"),4.39,new ArrayList<String>(Arrays.asList("Herning","Horsens","Ballerup","Odense","Hvidovre")),5383532000294l,668.50,Instant.parse("2021-05-22T00:38:32.230983"),Instant.parse("2022-11-04T18:03:11.008047"),"PC/Laptops","Acer Aspire One D150-1Bw Netbook 25.6 cm (10.1\") Intel Atom\u00ae 1 GB DDR2-SDRAM Windows XP Home Edition White","Acer Aspire One D150-1Bw, Intel Atom\u00ae, 1.66 GHz, 25.6 cm (10.1\"), 1024 x 600 pixels, 1 GB, Windows XP Home Edition Acer Aspire One D150-1Bw. Product type: Netbook. Processor family: Intel Atom\u00ae, Processor model: N280, Processor frequency: 1.66 GHz. Display diagonal: 25.6 cm (10.1\"), Display resolution: 1024 x 600 pixels. Internal memory: 1 GB, Internal memory type: DDR2-SDRAM. Operating system installed: Windows XP Home Edition. Product colour: White. Weight: 1.33 kg");
        products.add(tmp);
        tmp = new Product(UUID.fromString("941884b6-76d4-48a7-9d52-06fc2fb61b4b"),3.694,new ArrayList<String>(Arrays.asList("Herning","Ballerup","Roskilde","Aalborg","Hvidovre","Randers","Greve","Frederiksberg","Vejle","Odense")),5893702118785l,500.00,Instant.parse("2020-01-06T09:44:09.945549"),Instant.parse("2022-03-01T20:39:47.173347"),"PC/Laptops","Acer Aspire One AOD255E-13248 Netbook 25.6 cm (10.1\") Intel Atom\u00ae 1 GB DDR3-SDRAM 250 GB Windows 7 Starter Black","Acer Aspire One AOD255E-13248, Intel Atom\u00ae, 1.66 GHz, 25.6 cm (10.1\"), 1024 x 600 pixels, 1 GB, 250 GB Acer Aspire One AOD255E-13248. Product type: Netbook. Processor family: Intel Atom\u00ae, Processor model: N455, Processor frequency: 1.66 GHz. Display diagonal: 25.6 cm (10.1\"), Display resolution: 1024 x 600 pixels. Internal memory: 1 GB, Internal memory type: DDR3-SDRAM. Total storage capacity: 250 GB. Operating system installed: Windows 7 Starter. Product colour: Black. Weight: 1.25 kg");
        products.add(tmp);
        tmp = new Product(UUID.fromString("1cc20f06-d890-496a-9991-91b452d22cb5"),3.385,new ArrayList<String>(Arrays.asList("Naestved","Vejle","Herning","Randers","Roskilde","Frederiksberg","Odense","Arhus","Greve")),7896125155540l,963.00,Instant.parse("2018-08-21T13:46:21.391069"),Instant.parse("2023-02-23T08:11:46.170382"),"PC/Desktops","Lenovo IdeaCentre K415","Lenovo IdeaCentre K415 Lenovo IdeaCentre K415");
        products.add(tmp);
        tmp = new Product(UUID.fromString("a5d5830c-b0aa-43b3-8d22-9821730c72f3"),4.214,new ArrayList<String>(Arrays.asList()),9701071561001l,1150.00,Instant.parse("2020-10-26T18:57:00.802270"),Instant.parse("2022-12-04T21:49:21.970039"),"PC/Desktops","Lenovo IdeaCentre K450","Lenovo IdeaCentre K450 Lenovo IdeaCentre K450");
        products.add(tmp);
        tmp = new Product(UUID.fromString("2a0ec768-3f25-494e-9bc1-842278995f39"),4.67,new ArrayList<String>(Arrays.asList()),2712308065633l,1099.50,Instant.parse("2020-01-02T04:37:37.646341"),Instant.parse("2024-12-23T03:19:13.706475"),"PC/Desktops","HP EliteDesk 805 G8 DDR4-SDRAM 5650GE mini PC AMD Ryzen 5 PRO 16 GB 256 GB SSD Windows 10 Pro Black","HP EliteDesk 805 G8, 3.4 GHz, AMD Ryzen 5 PRO, 5650GE, 16 GB, 256 GB, Windows 10 Pro HP EliteDesk 805 G8. Processor frequency: 3.4 GHz, Processor family: AMD Ryzen 5 PRO, Processor model: 5650GE. Internal memory: 16 GB, Internal memory type: DDR4-SDRAM, Memory clock speed: 3200 MHz. Total storage capacity: 256 GB, Storage media: SSD. On-board graphics adapter model: AMD Radeon Graphics. Operating system installed: Windows 10 Pro, Operating system architecture: 64-bit. Power supply: 65 W. Chassis type: mini PC. Product type: Mini PC. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("a6381ade-870f-476d-98b1-79477abc15ae"),3.294,new ArrayList<String>(Arrays.asList("Esbjerg","Aalborg","Frederiksberg","Hvidovre","Herning","Charlottenlund","Naestved","Randers","Ballerup")),6818923646212l,1099.90,Instant.parse("2020-03-06T09:44:46.870672"),Instant.parse("2022-03-22T16:53:25.144108"),"PC/Desktops","Acer Veriton S670G DDR3-SDRAM Q8400 SFF Intel\u00ae Core\u21222 Quad 4 GB 320 GB Windows 7 Professional PC","Acer Veriton S670G, 2.66 GHz, Intel\u00ae Core\u21222 Quad, 4 GB, 320 GB, DVD\u00b1RW, Windows 7 Professional Acer Veriton S670G. Processor frequency: 2.66 GHz, Processor family: Intel\u00ae Core\u21222 Quad, Processor model: Q8400. Internal memory: 4 GB, Internal memory type: DDR3-SDRAM. Total storage capacity: 320 GB, Card reader integrated, Optical drive type: DVD\u00b1RW. Discrete graphics adapter model: Intel\u00ae GMA 4500M. Operating system installed: Windows 7 Professional. Power supply: 300 W. Chassis type: SFF. Product type: PC");
        products.add(tmp);
        tmp = new Product(UUID.fromString("72b6873e-3427-443d-ad79-8d4f8c5fedde"),2.896,new ArrayList<String>(Arrays.asList("Aalborg","Vejle","Charlottenlund","Esbjerg")),7775880493176l,600.50,Instant.parse("2020-10-27T07:58:08.761142"),Instant.parse("2025-12-27T15:36:31.278251"),"PC/Desktops","Acer Aspire XC-704 DDR3L-SDRAM J3060 Intel\u00ae Celeron\u00ae 4 GB 500 GB HDD FreeDOS PC Black","Acer Aspire XC-704, 1.6 GHz, Intel\u00ae Celeron\u00ae, 4 GB, 500 GB, DVD Super Multi, FreeDOS Acer Aspire XC-704. Processor frequency: 1.6 GHz, Processor family: Intel\u00ae Celeron\u00ae, Processor model: J3060. Internal memory: 4 GB, Internal memory type: DDR3L-SDRAM, Memory clock speed: 1600 MHz. Total storage capacity: 500 GB, Storage media: HDD, Card reader integrated, Optical drive type: DVD Super Multi. On-board graphics adapter model: Intel\u00ae HD Graphics 400. Operating system installed: FreeDOS. Power supply: 65 W. Product type: PC. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("c67cb89a-c0c6-4234-a211-96b3cc543945"),1.898,new ArrayList<String>(Arrays.asList("Vejle","Randers","Naestved")),4168099575703l,599.75,Instant.parse("2021-08-03T11:08:20.905382"),Instant.parse("2022-05-04T21:43:26.781717"),"PC/Desktops","Acer Aspire C-120 DDR3-SDRAM A10-7800 AMD A10 16 GB 1128 GB HDD+SSD Windows 8.1 PC Black","Acer Aspire C-120, 3.5 GHz, AMD A10, 16 GB, 1128 GB, DVD Super Multi, Windows 8.1 Acer Aspire C-120. Processor frequency: 3.5 GHz, Processor family: AMD A10, Processor model: A10-7800. Internal memory: 16 GB, Internal memory type: DDR3-SDRAM. Total storage capacity: 1128 GB, Storage media: HDD+SSD, Optical drive type: DVD Super Multi. Discrete graphics adapter model: AMD Radeon R7 240. Wi-Fi. Operating system installed: Windows 8.1, Operating system architecture: 64-bit. Power supply: 220 W. Product type: PC. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("3240d5f6-8c92-410f-a6d3-c6b7a1b71678"),4.632,new ArrayList<String>(Arrays.asList("Esbjerg","Aalborg","Naestved","Charlottenlund","Silkeborg","Greve","Odense","Arhus","Frederiksberg","Randers","Kolding","Ballerup","Herning","Roskilde","Hvidovre")),4817780380163l,5575.15,Instant.parse("2019-08-20T19:58:13.445409"),Instant.parse("2022-08-13T11:58:17.604564"),"Monitors","HP Pavilion g6-1165sa Notebook 39.6 cm (15.6\") Intel\u00ae Pentium\u00ae 4 GB DDR3-SDRAM 750 GB HDD Windows 7 Home Premium Red","HP Pavilion g6-1165sa, Intel\u00ae Pentium\u00ae, 2 GHz, 39.6 cm (15.6\"), 1366 x 768 pixels, 4 GB, 750 GB HP Pavilion g6-1165sa. Product type: Notebook, Form factor: Clamshell. Processor family: Intel\u00ae Pentium\u00ae, Processor model: B940, Processor frequency: 2 GHz. Display diagonal: 39.6 cm (15.6\"), Display resolution: 1366 x 768 pixels. Internal memory: 4 GB, Internal memory type: DDR3-SDRAM. Total storage capacity: 750 GB, Storage media: HDD, Optical drive type: DVD Super Multi DL. On-board graphics adapter model: Intel\u00ae HD Graphics. Operating system installed: Windows 7 Home Premium. Product colour: Red");
        products.add(tmp);
        tmp = new Product(UUID.fromString("235a8c5c-38e2-4440-a6c1-24046edd006d"),2.492,new ArrayList<String>(Arrays.asList()),9798237289282l,3299.95,Instant.parse("2020-05-15T07:44:53.749272"),Instant.parse("2024-03-29T08:57:20.578458"),"Monitors","HP 27w 68.6 cm (27\") 1920 x 1080 pixels Full HD LED Black","HP 27w, 68.6 cm (27\"), 1920 x 1080 pixels, Full HD, LED, 5 ms, Black HP 27w. Display diagonal: 68.6 cm (27\"), Display resolution: 1920 x 1080 pixels, HD type: Full HD, Display technology: LED, Response time: 5 ms, Native aspect ratio: 16:9. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("c191c7a6-1d0e-4b5b-a080-3db6ef558937"),3.765,new ArrayList<String>(Arrays.asList("Naestved")),6293357274277l,1390.25,Instant.parse("2017-11-11T22:01:48.278927"),Instant.parse("2022-09-20T21:24:54.803835"),"Monitors","HP 27wm 68.6 cm (27\") 1920 x 1080 pixels Full HD LED Black","HP 27wm, 68.6 cm (27\"), 1920 x 1080 pixels, Full HD, LED, 7 ms, Black HP 27wm. Display diagonal: 68.6 cm (27\"), Display resolution: 1920 x 1080 pixels, HD type: Full HD, Display technology: LED, Response time: 7 ms, Native aspect ratio: 16:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. Built-in speaker(s). VESA mounting. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("5de4f3b1-2c7f-4a8c-83f9-6ed927d619aa"),4.016,new ArrayList<String>(Arrays.asList("Greve","Charlottenlund","Herning","Ballerup","Vejle","Hvidovre","Aalborg","Arhus","Randers","Silkeborg","Horsens","Odense","Naestved")),9803056061992l,590.15,Instant.parse("2021-11-27T16:22:06.243208"),Instant.parse("2023-01-18T20:52:25.476729"),"Monitors","Samsung LC27FG73FQMXUF computer monitor 68.6 cm (27\") 1920 x 1080 pixels Full HD QLED Black, Blue","Samsung LC27FG73FQMXUF, 68.6 cm (27\"), 1920 x 1080 pixels, Full HD, QLED, 1 ms, Black, Blue Samsung LC27FG73FQMXUF. Display diagonal: 68.6 cm (27\"), Display resolution: 1920 x 1080 pixels, HD type: Full HD, Display technology: QLED, Response time: 1 ms, Native aspect ratio: 16:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. VESA mounting, Height adjustment. Product colour: Black, Blue");
        products.add(tmp);
        tmp = new Product(UUID.fromString("67d7b470-eebe-4da9-8b9e-72b522a7accf"),4.749,new ArrayList<String>(Arrays.asList("Silkeborg","Vejle","Ballerup")),5475595773779l,5290.50,Instant.parse("2020-09-03T08:18:35.837048"),Instant.parse("2025-02-13T20:04:44.826482"),"Monitors","Samsung LU32H850UMU computer monitor 80 cm (31.5\") 3840 x 2160 pixels 4K Ultra HD LED Black, Silver","Samsung LU32H850UMU, 80 cm (31.5\"), 3840 x 2160 pixels, 4K Ultra HD, LED, 4 ms, Black, Silver Samsung LU32H850UMU. Display diagonal: 80 cm (31.5\"), Display resolution: 3840 x 2160 pixels, HD type: 4K Ultra HD, Display technology: LED. Display: LED. Response time: 4 ms, Native aspect ratio: 16:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. Built-in USB hub, USB hub version: 3.2 Gen 1 (3.1 Gen 1). VESA mounting, Height adjustment. Product colour: Black, Silver");
        products.add(tmp);
        tmp = new Product(UUID.fromString("aeae0025-a7a7-4a65-b0c9-a1b92c113bbc"),2.803,new ArrayList<String>(Arrays.asList("Vejle","Silkeborg","Naestved","Aalborg","Greve","Odense","Esbjerg","Ballerup")),6700000800566l,899.50,Instant.parse("2021-08-04T08:56:59.461236"),Instant.parse("2022-10-18T20:48:48.012686"),"Monitors","Acer EI491CR P 124.5 cm (49\") 3840 x 1080 pixels LED Black","Acer EI491CR P, 124.5 cm (49\"), 3840 x 1080 pixels, LED, 4 ms, Black Acer EI491CR P. Display diagonal: 124.5 cm (49\"), Display resolution: 3840 x 1080 pixels, Display technology: LED, Response time: 4 ms, Native aspect ratio: 32:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. Built-in speaker(s). VESA mounting. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("f3d5118f-be71-44c0-bff8-71e924ee66f5"),3.685,new ArrayList<String>(Arrays.asList("Kolding","Greve","Charlottenlund","Ballerup","Herning")),1887005204119l,4700.50,Instant.parse("2019-05-22T22:30:40.197647"),Instant.parse("2022-04-20T17:51:21.886041"),"Monitors","DELL UltraSharp U2414H 60.5 cm (23.8\") 1920 x 1080 pixels Full HD LED Black","DELL UltraSharp U2414H, 60.5 cm (23.8\"), 1920 x 1080 pixels, Full HD, LED, 8 ms, Black DELL UltraSharp U2414H. Display diagonal: 60.5 cm (23.8\"), Display resolution: 1920 x 1080 pixels, HD type: Full HD, Display technology: LED. Display: LED. Response time: 8 ms, Native aspect ratio: 16:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. USB hub version: 3.2 Gen 1 (3.1 Gen 1). VESA mounting. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("c11d8cb8-e07f-4fae-94b2-4944b30f93c6"),1.518,new ArrayList<String>(Arrays.asList("Frederiksberg","Randers","Herning","Vejle","Arhus","Naestved")),4947134453042l,695.15,Instant.parse("2021-10-19T17:25:43.264746"),Instant.parse("2024-12-06T02:54:09.280218"),"Monitors","MSI Optix MAG272 68.6 cm (27\") 1920 x 1080 pixels Full HD Black","MSI Optix MAG272, 68.6 cm (27\"), 1920 x 1080 pixels, Full HD, 1 ms, Black MSI Optix MAG272. Display diagonal: 68.6 cm (27\"), Display resolution: 1920 x 1080 pixels, HD type: Full HD, Display surface: Matt, Response time: 1 ms, Native aspect ratio: 16:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. Built-in USB hub, USB hub version: 2.0. VESA mounting. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("a5dcdfe2-3d64-4976-ae8c-a1068aa2665e"),5.0,new ArrayList<String>(Arrays.asList("Greve")),5989703168505l,4999.90,Instant.parse("2020-12-23T11:31:48.125363"),Instant.parse("2023-08-16T21:41:04.372543"),"Monitors","MSI MPG341CQR 86.4 cm (34\") 3440 x 1440 pixels UltraWide Quad HD LCD Black","MSI MPG341CQR, 86.4 cm (34\"), 3440 x 1440 pixels, UltraWide Quad HD, LCD, 1 ms, Black MSI MPG341CQR. Display diagonal: 86.4 cm (34\"), Display resolution: 3440 x 1440 pixels, HD type: UltraWide Quad HD, Display technology: LCD, Display surface: Matt, Response time: 1 ms, Native aspect ratio: 21:9, Viewing angle, horizontal: 178\u00b0, Viewing angle, vertical: 178\u00b0. Built-in USB hub, USB hub version: 3.2 Gen 1 (3.1 Gen 1). VESA mounting, Height adjustment. Product colour: Black");
        products.add(tmp);
        tmp = new Product(UUID.fromString("1ed4a221-0434-4d0d-91c8-1c4089b65f77"),3.762,new ArrayList<String>(Arrays.asList("Horsens","Hvidovre","Charlottenlund","Arhus","Vejle","Greve","Herning","Aalborg")),1423639749599l,790.15,Instant.parse("2021-03-09T22:32:39.464157"),Instant.parse("2022-10-07T14:43:04.749779"),"Components/Storage/Harddrives","Lenovo FRU75Y5085 internal hard drive 320 GB","Lenovo FRU75Y5085, 320 GB, 5400 RPM Lenovo FRU75Y5085. HDD capacity: 320 GB, HDD speed: 5400 RPM");
        products.add(tmp);
        tmp = new Product(UUID.fromString("4201c327-3c0f-43f9-9b46-8fb0ab5df761"),4.322,new ArrayList<String>(Arrays.asList("Horsens","Greve","Vejle")),8660238508529l,550.99,Instant.parse("2021-04-22T16:05:03.603605"),Instant.parse("2022-12-24T15:26:45.525917"),"Components/Storage/Harddrives","HP 600GB SAS 15K SFF Hard Drive","HP 600GB SAS 15K SFF Hard Drive, 2.5\", 600 GB, 15000 RPM HP 600GB SAS 15K SFF Hard Drive. HDD size: 2.5\", HDD capacity: 600 GB, HDD speed: 15000 RPM");
        products.add(tmp);
        tmp = new Product(UUID.fromString("589347fc-07fe-4e1d-9f6c-1f99b1322a85"),3.601,new ArrayList<String>(Arrays.asList("Kolding","Ballerup","Randers","Horsens","Frederiksberg","Aalborg","Roskilde","Greve","Herning","Odense","Vejle","Hvidovre","Naestved","Arhus","Esbjerg")),8042105740230l,1400.25,Instant.parse("2022-02-02T18:32:44.372493"),Instant.parse("2022-06-21T21:35:49.750531"),"Components/Storage/SSDs","Lenovo 45N8296-RFB internal solid state drive 128 GB","Lenovo 45N8296-RFB, 128 GB Lenovo 45N8296-RFB. SSD capacity: 128 GB");
        products.add(tmp);
        tmp = new Product(UUID.fromString("06d980d7-8645-4fba-b688-3c51dcfbf69d"),3.656,new ArrayList<String>(Arrays.asList("Frederiksberg","Aalborg")),2266095980797l,2676.25,Instant.parse("2021-05-22T16:51:04.564621"),Instant.parse("2023-01-20T15:46:30.781883"),"Components/Storage/SSDs","HP Z Turbo Drive M.2 256 GB PCI Express 3.0 NVMe","HP Z Turbo Drive, 256 GB, M.2 HP Z Turbo Drive. SSD capacity: 256 GB, SSD form factor: M.2, Component for: Server/workstation");
        products.add(tmp);
        tmp = new Product(UUID.fromString("ce8b01ec-1a52-415a-86a1-557d3f8f9a1e"),4.639,new ArrayList<String>(Arrays.asList("Arhus","Vejle","Kolding","Greve","Silkeborg","Aalborg","Roskilde","Ballerup","Horsens","Frederiksberg")),1389313048930l,2925.75,Instant.parse("2022-01-07T17:36:19.217460"),Instant.parse("2026-02-04T08:19:06.205786"),"Components/Storage/SSDs","HP 2JB96AA internal solid state drive 2.5\" 512 GB Serial ATA III TLC","HP 2JB96AA, 512 GB, 2.5\", 530 MB/s, 6 Gbit/s HP 2JB96AA. SSD capacity: 512 GB, SSD form factor: 2.5\", Read speed: 530 MB/s, Write speed: 515 MB/s, Data transfer rate: 6 Gbit/s, Component for: Notebook");
        products.add(tmp);
        tmp = new Product(UUID.fromString("14a3a69e-72c8-4480-93e8-4074f660cccd"),3.811,new ArrayList<String>(Arrays.asList("Herning","Kolding","Randers","Charlottenlund","Ballerup","Esbjerg","Horsens","Vejle","Frederiksberg","Hvidovre","Silkeborg")),5379511953774l,500.99,Instant.parse("2021-08-02T10:32:08.418359"),Instant.parse("2023-10-20T12:45:53.633207"),"Components/Processors","Lenovo Xeon E5-2440 processor","Lenovo Xeon E5-2440 Lenovo Xeon E5-2440");
        products.add(tmp);
        tmp = new Product(UUID.fromString("19e56a2a-3718-4e58-9c08-283945cee02f"),3.571,new ArrayList<String>(Arrays.asList("Horsens","Esbjerg","Vejle","Herning","Frederiksberg","Randers","Aalborg","Ballerup","Greve","Naestved","Odense","Silkeborg","Kolding","Arhus","Charlottenlund","Roskilde")),9180197447640l,1250.75,Instant.parse("2021-03-22T08:55:41.878720"),Instant.parse("2023-04-03T06:55:28.683599"),"Components/Processors","Intel Xeon X3320 processor 2.5 GHz 6 MB L2","Intel Xeon X3320, Intel\u00ae Xeon\u00ae, LGA 775 (Socket T), 45 nm, Intel, X3320, 2.5 GHz Intel Xeon X3320. Processor family: Intel\u00ae Xeon\u00ae, Processor socket: LGA 775 (Socket T), Processor lithography: 45 nm. Market segment: Server, Number of Processing Die Transistors: 456 M, Processing Die size: 164 mm\u00b2. Package type: Retail box. Images Type Map: <div><img src=\"https://ark.intel.com/inc/images/diagrams/diagram-5.gif\" title=\"Block Diagram\"...");
        products.add(tmp);
        tmp = new Product(UUID.fromString("8e8bab75-0d9f-4de6-8f40-fb0618083cd2"),3.433,new ArrayList<String>(Arrays.asList("Ballerup","Roskilde","Silkeborg","Hvidovre","Kolding","Aalborg","Naestved","Arhus","Randers","Charlottenlund","Vejle","Frederiksberg","Herning","Greve","Horsens","Esbjerg")),7401376929637l,1699.90,Instant.parse("2020-08-10T16:30:52.095317"),Instant.parse("2022-03-23T04:33:45.841739"),"Components/Processors","Intel Core 2 Duo E6600 2.4GHz processor 4 MB L2 Box","Intel Core 2 Duo E6600 2.4GHz, Intel\u00ae Core\u21222 Duo, LGA 775 (Socket T), 65 nm, Intel, 2.4 GHz, 4 MB Intel Core 2 Duo E6600 2.4GHz. Processor family: Intel\u00ae Core\u21222 Duo, Processor socket: LGA 775 (Socket T), Processor lithography: 65 nm");
        products.add(tmp);
        tmp = new Product(UUID.fromString("6f83587c-aed4-4138-ab80-7f434ccd4b12"),4.328,new ArrayList<String>(Arrays.asList()),3107927297548l,2395.95,Instant.parse("2019-07-23T15:08:32.827936"),Instant.parse("2025-05-10T03:05:40.044387"),"Components/Processors","Intel Core i5-7600K processor 3.8 GHz 6 MB Smart Cache Box","Intel Core i5-7600K, Intel Core i5, LGA 1151 (Socket H4), 14 nm, Intel, i5-7600K, 3.8 GHz Intel Core i5-7600K. Processor family: Intel Core i5, Processor socket: LGA 1151 (Socket H4), Processor lithography: 14 nm. Memory channels: Dual-channel, Maximum internal memory supported by processor: 64 GB, Memory types supported by processor: DDR4-SDRAM,DDR3L-SDRAM. On-board graphics adapter model: Intel\u00ae HD Graphics 630, Maximum on-board graphics adapter memory: 64 GB, On-board graphics adapter outputs supported: DisplayPort,Embedded DisplayPort (eDP),HDMI. Market segment: Desktop, PCI Express configurations: 1x16,2x8,1x8+2x4, Supported instruction sets: SSE4.1,SSE4.2,AVX 2.0. Intel\u00ae Turbo Boost Technology 2.0 frequency: 4.2 GHz");
        products.add(tmp);
        tmp = new Product(UUID.fromString("64414b11-81bb-47f4-8589-430168fc5ea0"),3.344,new ArrayList<String>(Arrays.asList("Kolding","Arhus")),8311163628423l,575.95,Instant.parse("2021-12-09T22:29:05.909699"),Instant.parse("2022-04-27T05:16:05.362723"),"Components/Processors","DELL Intel Xeon E5620 processor","DELL Intel Xeon E5620, Intel\u00ae Xeon\u00ae 5000 Sequence, u, u, u, u, u DELL Intel Xeon E5620. Processor family: Intel\u00ae Xeon\u00ae 5000 Sequence. Intel Identity Protection Technology version: u, Intel Secure Boot Technology version: u, Intel Secure Key Technology version: u");
                products.add(tmp);


    }

}
