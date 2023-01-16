package com.dwarfeng.acckeeper.impl.handler;

import com.dwarfeng.acckeeper.stack.exception.ProtectorException;
import com.dwarfeng.acckeeper.stack.handler.Protector;

/**
 * 保护器构造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface ProtectorMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的保护器信息生成一个保护器对象。
     *
     * <p>
     * 可以保证传入的保护器信息中的类型是支持的。
     *
     * @param type  保护器类型。
     * @param param 保护器参数。
     * @return 制造出的保护器。
     * @throws ProtectorException 保护器异常。
     */
    Protector makeProtector(String type, String param) throws ProtectorException;
}
