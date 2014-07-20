/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course.labs.activitylab;

/**
 *
 * @author mark.mullally
 */
public enum ActivityLifecyclePhase
{
    create("onCreate()"),
    start("onStart()"),
    stop("onStop()"),
    restart("onRestart()"),
    pause("onPause()"),
    resume("onResume()"),
    destroy("onDestroy()");

    private final String displayName;

    private ActivityLifecyclePhase(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

}
