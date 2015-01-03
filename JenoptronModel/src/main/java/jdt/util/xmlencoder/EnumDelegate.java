package jdt.util.xmlencoder;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;

public class EnumDelegate<T extends Enum<T>>
extends PersistenceDelegate
{
    public static <TT extends Enum<TT>> EnumDelegate<TT> create(final Class<TT> type)
    {
        return new EnumDelegate<TT>(type);
    }
 
    private final Class<T> type;
 
    public EnumDelegate(final Class<T> type)
    {
    	super();
        this.type = type;
    }
 
    @Override
	protected boolean mutatesTo(final Object oldInstance, final Object newInstance)
    {
        return oldInstance == newInstance;
    }
 
    @Override
    /*
     * @param out
     * @param Enoder
     */
	protected Expression instantiate(final Object oldInstance, final Encoder out)
    {
        return new Expression(oldInstance, type, "valueOf", new Object[]{type.cast(oldInstance).name()});
    }
}

