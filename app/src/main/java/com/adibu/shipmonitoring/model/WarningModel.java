package com.adibu.shipmonitoring.model;

public class WarningModel {

    String categories;
    String title;
    String message;
    String recommendation;

    private final String COOLING = "Cooling";
    private final String FUELOIL = "Fuel Oil";
    private final String LUBEOIL = "Lube Oil";
    private final String MAINENGINE = "Main Engine";

    public WarningModel(String key, float value) {
        getWarning(key, value);
    }

    private void getWarning(String key, float value) {
        switch (key) {
            //COOLING
            case "fwtempafterme":
                categories = COOLING;
                title = "FW Temp. After ME";
                if (value < 40) {
                    message = "Abnormal. Suhu<40°C";
                    recommendation =
                            "Cek pada pre heater unit (KHUSUS STARTING)\n" +
                            "       a. Cek pada pompa preheater\n" +
                            "       b. Cek pada kelistrikan pompa preheater\n" +
                            "               - Cek pada Terminal Box\n" +
                            "               - Cek pada kabel aliran listrik\n" +
                            "               - Cek pada sumber tegangan\n" +
                            "       c. Cek pada  valve pada preheater\n" +
                            "               - Cek pemutar valve\n" +
                            "               - Cek bearing valve\n" +
                            "       d. Cek pada kelistrikan heater \n" +
                            "               - Cek terminal box heater\n" +
                            "               - Cek pada kabel alira\n" +
                            "               - Cek pada sumber aliran listrik\n";

                } else if (value > 90) {
                    message = "Abnormal. Suhu>90°C";
                    recommendation =
                            "1. Cek pada cooling unit sea water\n" +
                            "       a. Cek pada saluran pipa input engine\n" +
                            "       b. Cek pada komponen cooler/heat exchanger\n" +
                            "       c. Cek pompa cooler\n" +
                            "       d. Cek kelistrikan pompa cooler\n" +
                            "       e. Cek pada sumber tegangan\n" +
                            "2. Cek saluran pipa ke cylinder blok \n" +
                            "       a. Cek kebocoran pipa\n" +
                            "       b. Cek sambungan pipa\n" +
                            "       c. Cek flange pipe\n" +
                            "3. Cek sechect box\n" +
                            "       a. Cek filter sea chest\n" +
                            "               - Bersihkan filter\n" +
                            "       b. Cek valve sea chest\n" +
                            "               - Cek packing flange\n" +
                            "               - Cek putaran pembuka valve\n" +
                            "       c. Cek pada flange valve dan saluran pipa\n" +
                            "       d. Cek kotoran pada sea chest\n" +
                            "               - Beri tekanan sea chest dengan tekanan dari kompresor\n";
                }
                break;
            case "fwtempbeforeme":
                categories = COOLING;
                title = "FW Temp. Before ME";
                if (value < 30) {
                    message = "Abnormal. Suhu<30°C";
                    recommendation =
                            "1. Cek pada valve interlock unit\n" +
                            "       a. Cek saluran pipa\n" +
                            "       b. Cek pada flange (bisa jadi bocor)\n";
                } else if (value > 50) {
                    message = "Abnormal. Suhu>40°C";
                    recommendation =
                            "1. Cek pada heat exchanger sea water / cooler\n" +
                            "       a. Cek cooling coil\n" +
                            "       b. Cek kebocoran cooling coil\n" +
                            "2. Cek pompa sea water cooling\n" +
                            "       a. Cek terminal box motor pompa \n" +
                            "       b. Cek sumber tegangan di motor\n" +
                            "3. Cek valve pada instalasi cooler (buka/tutup)\n" +
                            "       a. Cek actuator (pneumatic)\n" +
                            "       b. Cek tekanan kompresor\n";
                }
                break;
            case "fwpressbeforeme":
                categories = COOLING;
                title = "FW Press Before ME";
                if (value < 40) {
                    message = "Abnormal. Tekanan<40Mpa";
                    recommendation =
                            "1. Cek pada pompa feed unit \n" +
                            "       a. Cek sumber tegangan pompa feed\n" +
                            "       b. Cek komponen pompa\n" +
                            "       c. Ganti pompa\n";
                } else if (value > 50) {
                    message = "Abnormal. Tekanan>50Mpa";
                    recommendation =
                            "1. Cek sumber tegangan pompa feed\n" +
                            "2. Cek komponen pompa\n" +
                            "3. Cek pressure valve sea water pump feed\n" +
                            "       a. Cek pressure valve\n" +
                            "       b. Cek komponen pressure valve\n" +
                            "4. Cek pressure valve sea water pump cooler\n" +
                            "       a. Cek pressure valve\n" +
                            "       b. Cek komponen pressure valve\n";
                }
                break;
            case "swpressbeforecu":
                categories = COOLING;
                title = "SW Press Before CU";
                if (value < 0.15) {
                    message = "Abnormal. Tekanan<0.15Mpa";
                    recommendation =
                            "1. Cek sechecs box\n" +
                            "       a. Cek filter sea chest\n" +
                            "               - Bersihkan filter\n" +
                            "       b.  Cek valve sea chest\n" +
                            "               - Cek packing flange\n" +
                            "               - Cek putaran pembuka valve\n" +
                            "       c.  Cek pada flange valve dan saluran pipa\n" +
                            "       d.  Cek kotoran pada sea chest\n" +
                            "               - Beri tekanan sea chest dengan tekanan dari kompresor\n";
                }
                break;
            case "voltankexp":
                categories = COOLING;
                title = "Vol. Tank Exp.";
                if (value < 50) {
                    message = "Abnormal. Volume<50%";
                    recommendation =
                            "1. Tambah air tawar\n" +
                            "2. Cek instalasi pipa tangka expansi\n" +
                            "3. Cek valve pada instalasi\n" +
                            "       a. Cek packing flange\n" +
                            "       b.Cek putaran pembuka valve\n";
                }
                break;
            //FUEL OIL
            case "fosupplypumppress":
                categories = FUELOIL;
                title = "Supply Pump Press";
                if(value<0.25) {
                    message = "Abnormal. Tekanan<0.25Mpa";
                    recommendation =
                            "1. Cek saluran pipa, flange dan valve.\n" +
                                    "2. Cek heater pada FO Storage Tank\n" +
                                    "3. Cek filter\n";
                }
                break;
            case "focirclpumppress":
                categories = FUELOIL;
                title = "Circl. Pump Press.";
                if(value<1.4) {
                    message = "Abnormal. Tekanan<1.4Mpa";
                    recommendation =
                            "1. Cek saluran pipa, valve dan flange\n" +
                            "2. Cek suhu pada storage tank\n";
                } else if(value>1.8) {
                    message = "Abnormal. Tekanan>1.8Mpa";
                    recommendation =
                            "1. Cek saluran pipa, valve dan flange\n" +
                            "2. Cek suhu pada storage tank\n";
                }
                break;
            case "fosettlingtanklvl":
                categories = FUELOIL;
                title = "Settling Tank Lvl";
                if(value<40) {
                    message = "Warning. Level<40cm";
                    recommendation =
                            "Refill Tank";
                } else if(value>190) {
                    message = "Warning. Level>190cm";
                    recommendation =
                            "Full Tank (Stop Pengisian)";
                }
                break;
            case "foservicetanklvl":
                categories = FUELOIL;
                title = "Service Tank Lvl";
                if(value<40) {
                    message = "Warning. Level<40cm";
                    recommendation =
                            "Refill Tank";
                } else if(value>190) {
                    message = "Warning. Level>190cm";
                    recommendation =
                            "Full Tank (Stop Pengisian)";
                }
                break;
            case "foservicetanktemp":
                categories = FUELOIL;
                title = "Service Tank Temp";
                if(value<70) {
                    message = "Abnormal. Suhu<70°C";
                    recommendation =
                            "1. Cek heater unit\n" +
                            "       a. Cek sumber tegangan dan saluran listrik\n" +
                            "       b. Cek terminal bo\n" +
                            "       c. Cek temperature indicator pada heater (40° - 90° C >> Normal)\n";
                } else if(value>90) {
                    message = "Abnormal. Suhu>90°C";
                    recommendation =
                            "1. Cek temperature indicator pada heater";
                }
                break;
            case "fosettlingtanktemp":
                categories = FUELOIL;
                title = "Settling Tank Temp.";
                if(value<50) {
                    message = "Abnormal. Suhu<50°C";
                    recommendation =
                            "1. Cek heater unit\n" +
                            "       a. Cek sumber tegangan dan saluran listrik\n" +
                            "       b. Cek terminal bo\n" +
                            "       c. Cek temperature indicator pada heater (40° - 90° C >> Normal)\n";
                } else if(value>70) {
                    message = "Abnormal. Suhu>70°C";
                    recommendation =
                            "1. Cek temperature indicator pada heater";
                }
                break;
            case "fotempbeforeme":
                categories = FUELOIL;
                title = "Temp. Before ME";
                if(value<130) {
                    message = "Abnormal. Suhu<130°C";
                    recommendation =
                            "1.Cek heater unit\n" +
                            "       a. Cek sumber tegangan dan saluran listrik\n" +
                            "       b. Cek terminal box\n" +
                            "       c. Cek indicator suhu pada heater\n";
                } else if(value>150) {
                    message = "Abnormal. Suhu>150°C";
                    recommendation =
                            "1.Cek heater unit\n" +
                            "       a. Cek temperature indicator pada heater";
                }
                break;
            //LUBRIICATING OIL
            case "lotempbeforeme":
                categories = LUBEOIL;
                title = "Temp Before ME";
                if(value<35) {
                    message = "Abnormal. Suhu<35°C";
                    recommendation =
                            "1. Cek pada pompa preheater\n" +
                            "2. Cek pada kelistrikan pompa preheater\n" +
                            "       a. Cek pada terminal box\n" +
                            "       b. Cek pada kabel aliran listrik\n" +
                            "       c. Cek pada sumber tegangan\n" +
                            "3. Cek pada  valve pada preheater\n" +
                            "       a. Cek pemutar valve\n" +
                            "       b. Cek bearing valve\n";
                } else if(value>50) {
                    message = "Abnormal. Suhu>50°C";
                    recommendation =
                            "1. Cek temperature indicator pada heater";
                }
                break;
            case "lotempafterme":
                categories = LUBEOIL;
                title = "Temp After ME";
                if(value<75) {
                    message = "Abnormal. Suhu<75°C";
                    recommendation =
                            "1. Cek sistem perpipaan \n" +
                            "2. Cek Cooling Unit\n";
                } else if(value>90) {
                    message = "Abnormal. Suhu>90°C";
                    recommendation =
                            "1. Cek oli tercampur air/tidak\n" +
                            "2. Cek thermostart\n" +
                            "3. Cek piston\n" +
                            "4. Cek cooling unit\n";
                }
                break;
            case "lotransferpump":
                categories = LUBEOIL;
                title = "Transfer Pump";
                if(value<0.5) {
                    message = "Abnormal. Tekanan<0.5Mpa";
                    recommendation =
                            "1. Cek pompa transfer pump\n" +
                            "2. Cek pressure valve\n";
                } else if(value>0.7) {
                    message = "Abnormal. Tekanan>0.7Mpa";
                    recommendation =
                            "1. Cek pompa transfer pump\n" +
                            "2. Cek pressure valve\n";
                }
                break;
            case "locirclpumppress":
                categories = LUBEOIL;
                title = "Circl. Pump Press";
                if(value<1.4) {
                    message = "Abnormal. Tekanan<1.4Mpa";
                    recommendation =
                            "1. Cek pompa transfer pump\n" +
                            "2. Cek pressure valve\n";
                } else if(value>1.8) {
                    message = "Abnormal. Tekanan>1.8Mpa";
                    recommendation =
                            "1. Cek pompa transfer pump\n" +
                            "2. Cek pressure valve\n";
                }
                break;
            case "losumptanklvl":
                categories = LUBEOIL;
                title = "Sump Tank Lvl";
                if(value<30) {
                    message = "Abnormal. Level<30cm";
                    recommendation =
                            "Refill pelumas";
                } else if(value>120) {
                    message = "Abnormal. Level>120cm";
                    recommendation =
                            "1. Cek sistem perpipaan\n" +
                            "2. Cek valve lubrication system\n";
                }
                break;
            //MAIN ENGINE
            case "startingair":
                categories = MAINENGINE;
                title = "Starting Air";
                if(value<2) {
                    message = "Abnormal. Tekanan<2Mpa";
                    recommendation =
                            "1. Cek blade turbin turbocharger\n" +
                            "2. Cek sistem exhaust valve engine\n";
                } else if(value>4) {
                    message = "Abnormal. Tekanan>4Mpa";
                    recommendation =
                            "1. Cek setelan gas mesin\n" +
                            "2. Cek sistem exhaust valve engine\n";
                }
                break;
            case "gastempbefore":
                categories = MAINENGINE;
                title = "Gas Temp Before Turbo";
                if(value<30) {
                    message = "Abnormal. Temp<30°C";
                    recommendation =
                            "1. Cek LO system\n" +
                            "2. Cek cooling Unit LO\n" +
                            "3. Cek preheater unit";
                } else if(value>45) {
                    message = "Abnormal. Temp>45°C";
                    recommendation =
                            "1. Cek LO system\n" +
                            "2. Cek cooling system\n";
                }
                break;
            case "gastempafter":
                categories = MAINENGINE;
                title = "Gas Temp After Turbo";
                if(value<300) {
                    message = "Abnormal. Temp<300°C";
                    recommendation =
                            "1. Cek LO system\n" +
                            "2. Cek cooling unit LO\n";
                } else if(value>370) {
                    message = "Abnormal. Temp>370°C";
                    recommendation =
                            "1. Cek LO system\n" +
                            "2. Cek cooling system\n" +
                            "3. Cek ring piston\n";
                }
                break;
        }
    }

    public String getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getRecommendation() {
        return recommendation;
    }

    @Override
    public String toString() {
        return "WarningModel{" +
                "categories='" + categories + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


}
