/**
 * 
 */
package cn.edu.njupt.sc.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author smartzxy@gmail.com
 * 
 */
public class Interpreter {

	/**
	 * Interpret parameter like "xxx.yyy.zzz" to a list that consists of "xxx",
	 * "yyy"and"zzz".
	 * 
	 * @param arg
	 * @return
	 */
	public List<String> Command2List(String arg) {
		return Arrays.asList(this.Command2String(arg));
	}

	/**
	 * Interpret parameter like "xxx.yyy.zzz" to a String array that consists of
	 * "xxx", "yyy"and"zzz".
	 * 
	 * @param arg
	 * @return
	 */
	public String[] Command2String(String arg) {

		return arg.split("\\.");
	}
}
