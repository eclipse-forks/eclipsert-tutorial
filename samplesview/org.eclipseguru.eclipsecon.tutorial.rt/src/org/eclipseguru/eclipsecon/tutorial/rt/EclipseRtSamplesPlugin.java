package org.eclipseguru.eclipsecon.tutorial.rt;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EclipseRtSamplesPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipseguru.eclipsecon.tutorial.rt"; //$NON-NLS-1$

	// The shared instance
	private static EclipseRtSamplesPlugin plugin;

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EclipseRtSamplesPlugin getDefault() {
		return plugin;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
