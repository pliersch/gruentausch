package gruentausch.propertytest;

import org.eclipse.core.expressions.PropertyTester;

// TODO not used
public class SelectionPropertyTester extends PropertyTester {
	
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		System.out.println("tesdtz");
		return false;
	}
}
