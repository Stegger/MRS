package movierecsys.dal;

import java.util.Enumeration;
import java.util.Hashtable;

public abstract class ObjectPool<T>
{

    private long expirationTime;

    private Hashtable<T, Long> locked, unlocked;

    /**
     * Create an object pool.
     */
    public ObjectPool()
    {
        expirationTime = 30000; // 30 seconds
        locked = new Hashtable<T, Long>();
        unlocked = new Hashtable<T, Long>();
    }

    /**
     * Create a new resource.
     *
     * @return The newly created resource.
     */
    protected abstract T create();

    /**
     * Validate the resource.
     *
     * @param o The resource to validate.
     * @return True if the resource is valid.
     */
    public abstract boolean validate(T o);

    /**
     * Expire the resource.
     *
     * @param o The resource to expire.
     */
    public abstract void expire(T o);

    /**
     * Check out the resource so it can be used.
     *
     * @return The available resource.
     */
    public synchronized T checkOut()
    {
        long now = System.currentTimeMillis();
        T t;
        if (unlocked.size() > 0) //If there are available resources
        {
            Enumeration<T> e = unlocked.keys(); //Using the iterator pattern to go over all available resources
            while (e.hasMoreElements()) //While there are more resources available
            {
                t = e.nextElement(); //Get the next resource available
                if ((now - unlocked.get(t)) > expirationTime) //If the resource has expired:
                {
                    //Object has expired. We remove any reference to it and run the expiration rutine.
                    unlocked.remove(t);
                    expire(t);
                    t = null;
                } else //If the resource has NOT expired:
                {
                    if (validate(t)) //Is it still valid?
                    {
                        unlocked.remove(t); //We remove the object from the unlocked list
                        locked.put(t, now); //Put it in the locked list
                        return (t); //And hand it over to the client
                    } else
                    {
                        // object failed validation, so we remove any reference to it and run the expiration rutine.
                        unlocked.remove(t);
                        expire(t);
                        t = null;
                    }
                }
            }
        }
        // no objects available, create a new one
        t = create();
        locked.put(t, now);
        return (t); //And hand over the new object to the client
    }

    /**
     * Releases the resource t for future use.
     *
     * @param t The resource
     */
    public synchronized void checkIn(T t)
    {
        locked.remove(t);
        unlocked.put(t, System.currentTimeMillis());
    }
}
