/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.edu.njupt.sc.GUI;

import java.awt.EventQueue;

import cn.edu.njupt.sc.CPECore;

//import cn.edu.njupt.sc.CPECore;

/**
 *
 * @author smartzxy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
//    	EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new CPEFrame().setVisible(true);
//            }
//        });
//    	new CPECore().turnOn();
    	CPECore core = new CPECore();
		core.turnOn();
    }

}
