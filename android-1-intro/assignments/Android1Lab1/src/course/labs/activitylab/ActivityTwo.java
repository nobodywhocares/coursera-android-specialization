package course.labs.activitylab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ActivityTwo
        extends Activity
{

    // String for LogCat documentation
    private final static String TAG = "Lab-ActivityTwo";

    // Lifecycle counters
    // TODO:
    // Create counter variables for onCreate(), onRestart(), onStart() and
    // onResume(), called mCreate, etc.
    // You will need to increment these variables' values when their
    // corresponding lifecycle methods get called
    private final ActivityAuditInterceptor auditor;

    // TODO: Create variables for each of the TextViews, called
    // mTvCreate, etc. 
    private TextView mTvCreate;
    private TextView mTvStart;
    private TextView mTvResume;
    private TextView mTvRestart;

    public ActivityTwo()
    {
        this.auditor = new ActivityAuditInterceptor(this, TAG, new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent pce)
            {
                ActivityTwo.this.displayCounts(
                        ActivityLifecyclePhase.valueOf(pce.getPropertyName()), (Integer) pce.getNewValue());
            }            
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

	// TODO: Assign the appropriate TextViews to the TextView variables
        // Hint: Access the TextView by calling Activity's findViewById()
        // textView1 = (TextView) findViewById(R.id.textView1);
        mTvCreate = (TextView) findViewById(R.id.create);
        mTvStart = (TextView) findViewById(R.id.start);
        mTvResume = (TextView) findViewById(R.id.resume);
        mTvRestart = (TextView) findViewById(R.id.restart);
        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

		// TODO:
                // This function closes Activity Two
                // Hint: use Context's finish() method
                ActivityTwo.this.finish();
            }

        });

        // Check for previously saved state
        if (savedInstanceState != null)
        {

            // TODO:
            // Restore value of counters from saved state
            // Only need 4 lines of code, one for every count variable
            auditor.restoreInstanceState(savedInstanceState);

        }

	// TODO: Emit LogCat message
	// TODO:
        // Update the appropriate count variable
        // Update the user interface via the displayCounts() method
        auditor.audit(ActivityLifecyclePhase.create);
    }

    // Lifecycle callback methods overrides
    @Override
    public void onStart()
    {
        super.onStart();

	// TODO: Emit LogCat message
	// TODO:
        // Update the appropriate count variable
        // Update the user interface
        auditor.audit(ActivityLifecyclePhase.start);
    }

    @Override
    public void onResume()
    {
        super.onResume();

	// TODO: Emit LogCat message
	// TODO:
        // Update the appropriate count variable
        // Update the user interface
        auditor.audit(ActivityLifecyclePhase.resume);
    }

    @Override
    public void onPause()
    {
        super.onPause();

	// TODO: Emit LogCat message
        auditor.audit(ActivityLifecyclePhase.pause);
    }

    @Override
    public void onStop()
    {
        super.onStop();

	// TODO: Emit LogCat message
        auditor.audit(ActivityLifecyclePhase.stop);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

	// TODO: Emit LogCat message
	// TODO:
        // Update the appropriate count variable
        // Update the user interface
        auditor.audit(ActivityLifecyclePhase.restart);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

	// TODO: Emit LogCat message
        auditor.audit(ActivityLifecyclePhase.destroy);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {

	// TODO:
        // Save counter state information with a collection of key-value pairs
        // 4 lines of code, one for every count variable
        auditor.saveInstanceState(savedInstanceState);
    }

    // Updates the displayed counters
    public void displayCounts(ActivityLifecyclePhase lifeId, int count)
    {
        TextView tv = null;
        
        switch (lifeId)
        {
            case create:
                tv = mTvCreate;
                break;
            case start:
                tv = mTvStart;
                break;
            case resume:
                tv = mTvResume;
                break;
            case restart:
                tv = mTvRestart;
                break;
                      
        }
        
        if (null != tv)
        {
            tv.setText(new StringBuilder(lifeId.getDisplayName()).append(" calls: ").append(count).toString());
        }
    }

}
