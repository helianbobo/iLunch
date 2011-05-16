package cn.ilunch.exception

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/22/11
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
class EntityNotFoundException extends RuntimeException{
    def exceptionInfoMap
     public EntityNotFoundException(maps){
         exceptionInfoMap = maps
     }
}
