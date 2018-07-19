package org.qamation.web.page;

public interface Page extends IsReady {
	public void openPage(String url);
	public void refresh();
	public void goBack(String url);
	public String readTextFrom(String location);
	public String readTextFrom(String location, int length);
	public String getSource();
	public long getPageReadyTime();
}
