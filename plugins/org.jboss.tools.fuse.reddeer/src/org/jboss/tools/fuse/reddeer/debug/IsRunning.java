package org.jboss.tools.fuse.reddeer.debug;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Checks whether debugging is running
 * 
 * @author tsedmik
 */
public class IsRunning implements WaitCondition {

	@Override
	public boolean test() {

		if (new SuspendButton().isEnabled()) {
			AbstractWait.sleep(TimePeriod.SHORT);
			return true;
		}
		return false;
	}

	@Override
	public String description() {

		return "Debugger didn't running";
	}
}
