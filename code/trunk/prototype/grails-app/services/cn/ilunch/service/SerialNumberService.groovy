package cn.ilunch.service;

import java.util.Date;
import java.util.Random;

class SerialNumberService {


    public static final def REPOSITORY = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"] as String[];

    public static final def REPOLENGTH = REPOSITORY.length;

    //前六位+后四位

    def getCode(long l, Date date) {
        Random ran = new Random();
        ran.setSeed(date.getTime());
        String key = "";
        4.times {
            key += REPOSITORY[ran.nextInt(REPOLENGTH)];
        }

        ran.setSeed(l);
        2.times {
            key += REPOSITORY[ran.nextInt(REPOLENGTH)];
        }
        ran.setSeed(System.currentTimeMillis())
        4.times {
            key += REPOSITORY[ran.nextInt(REPOLENGTH)];
        }
        key
    }

    def send(sn) {

    }
}
