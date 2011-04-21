package cn.ilunch.domain

class Location {

    Double latitude
    Double longitude
    int status

    static int INUSE = 0
    static int DELETED = 1

    static constraints = {
         status(min: 0,max: 1)
    }
}
