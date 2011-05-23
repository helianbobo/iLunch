package cn.ilunch.service

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 5/23/11
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
class DeprecatedOrderException extends RuntimeException {
    def exceptionInfoMap

    DeprecatedOrderException(Map map) {

        super(map['message'])
        exceptionInfoMap = map
    }
}
