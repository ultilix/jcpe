/**
 * 
 */
package cn.edu.njupt.sc.GUI;

import cn.edu.njupt.sc.CPECore;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class CPEThread extends Thread {

	private CPEFrame gui;

	public CPEThread(CPEFrame gui) {
		this.gui = gui;
	}

	public void run() {
		CPECore core = new CPECore();
		core.turnOn(gui);
	}
}
