/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course.labs.activitylab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *
 * @author mark.mullally
 */
public class ActivityAuditInterceptor
{
    private final Activity source;
    private final String logTag;
    private final AtomicIntegerArray counts;
    private final PropertyChangeListener countChangeListener;

    public ActivityAuditInterceptor(Activity source, String logTag, PropertyChangeListener countChangeListener)
    {
        this.source = source;
        this.logTag = logTag;
        this.countChangeListener = countChangeListener;
        this.counts = new AtomicIntegerArray(ActivityLifecyclePhase.destroy.ordinal() + 1);
    }

    public void saveInstanceState(Bundle state)
    {
        for (ActivityLifecyclePhase lifeId : ActivityLifecyclePhase.values())
        {
            state.putInt(lifeId.name(), counts.get(lifeId.ordinal()));
        }
    }

    public void restoreInstanceState(Bundle state)
    {
        if (null != state)
        {
            for (ActivityLifecyclePhase lifeId : ActivityLifecyclePhase.values())
            {
                counts.set(lifeId.ordinal(), state.getInt(lifeId.name()));
                fireCountChangeEvent(lifeId, false);
            }
        }
    }

    public void audit(ActivityLifecyclePhase lifeId)
    {
        audit(lifeId, (Throwable) null);
    }

    public void audit(ActivityLifecyclePhase lifeId, Throwable ex)
    {
        switch (lifeId)
        {
            case create:
            case start:
            case resume:
            case restart:
                fireCountChangeEvent(lifeId, true);
                break;
            default:
                // no-op
                break;
        }
        Log.i(logTag, new StringBuilder(
                lifeId.getDisplayName()).append(" lifecycle callback method invoked.").toString());
    }

    protected void fireCountChangeEvent(ActivityLifecyclePhase lifeId, boolean increment)
    {
        int oldVal = counts.get(lifeId.ordinal());
        countChangeListener.propertyChange(
                new PropertyChangeEvent(source, lifeId.name(),
                        oldVal, increment ? counts.incrementAndGet(lifeId.ordinal()) : oldVal));
    }

}
