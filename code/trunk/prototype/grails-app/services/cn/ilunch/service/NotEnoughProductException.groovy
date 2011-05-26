package cn.ilunch.service

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/22/11
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
class NotEnoughProductException extends RuntimeException{
    def map

    NotEnoughProductException(Map map){
        this.map = map
    }
}
