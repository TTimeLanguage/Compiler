package CodeGenerator;

import Semantic.FunctionInfo;
import Semantic.FunctionSet;
import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Statements;
import Syntax.Type;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class DefinedFunction {
	private static final ParamDeclaration intParam = new ParamDeclaration(Type.INT, "dummy");
	private static final ParamDeclaration floatParam = new ParamDeclaration(Type.FLOAT, "dummy");
	private static final ParamDeclaration boolParam = new ParamDeclaration(Type.BOOL, "dummy");
	private static final ParamDeclaration charParam = new ParamDeclaration(Type.CHAR, "dummy");
	private static final ParamDeclaration dateParam = new ParamDeclaration(Type.DATE, "dummy");
	private static final ParamDeclaration timeParam = new ParamDeclaration(Type.TIME, "dummy");
	private static FunctionSet functionSet;
	private static HashMap<String, ArrayList<FunctionInfo>> overloadMap;

	public static final ArrayList<String> predefinedFunc = new ArrayList<>(Arrays.asList(
			"write", "read", "lf", "getHour", "getMin", "getSec", "getYear", "getMonth", "getDay",
			"addTime", "addDate", "subTime", "subDate", "mulTime", "divTime", "modTime",
			"validTime", "validDate",
			"addFloat", "subFloat", "mulFloat", "divFloat", "modFloat", "F2I", "I2F"
	));

	public static void defineFunc(FunctionSet functionSet, HashMap<String, ArrayList<FunctionInfo>> overloadMap) {
		DefinedFunction.functionSet = functionSet;
		DefinedFunction.overloadMap = overloadMap;

		createFunc(Type.VOID, "write", intParam);
		createFunc(Type.VOID, "write", boolParam);
		createFunc(Type.VOID, "write", charParam);
		createFunc(Type.VOID, "write", dateParam);
		createFunc(Type.VOID, "write", timeParam);
		createFunc(Type.INT, "read", intParam);
		createFunc(Type.VOID, "lf");
		
		createFunc(Type.INT, "getHour", timeParam);
		createFunc(Type.INT, "getMin", timeParam);
		createFunc(Type.INT, "getSec", timeParam);
		createFunc(Type.INT, "getYear", dateParam);
		createFunc(Type.INT, "getMonth", dateParam);
		createFunc(Type.INT, "getDay", dateParam);
		
		createFunc(Type.TIME, "addTime", timeParam, timeParam);
		createFunc(Type.TIME, "subTime", timeParam, timeParam);
		createFunc(Type.TIME, "mulTime", timeParam, intParam);
		createFunc(Type.TIME, "divTime", timeParam, intParam);
		createFunc(Type.TIME, "modTime", timeParam, intParam);
		createFunc(Type.TIME, "validTime", timeParam);

		createFunc(Type.DATE, "addDate", dateParam, dateParam);
		createFunc(Type.DATE, "subDate", dateParam, dateParam);
		createFunc(Type.DATE, "validDate", dateParam);
	}

	private static void createFunc(Type type, String name, ParamDeclaration... params) {
		ArrayList<ParamDeclaration> param = new ArrayList<>();
		Collections.addAll(param, params);

		FunctionDeclaration dec = new FunctionDeclaration(type, name, param, null);
		FunctionInfo info = new FunctionInfo(dec);

		functionSet.add(info);

		ArrayList<FunctionInfo> functionList = new ArrayList<>();
		functionList.add(info);

		overloadMap.put(name, functionList);
	}

	protected static void getHour() {
		CodeGenerator.genFunc("getHour", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 3600);
		CodeGenerator.genCode("div");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void getMin() {
		CodeGenerator.genFunc("getMin", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 3600);
		CodeGenerator.genCode("mod");
		CodeGenerator.genCode("ldc", 60);
		CodeGenerator.genCode("div");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void getSec() {
		CodeGenerator.genFunc("getSec", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 60);
		CodeGenerator.genCode("div");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void getYear() {
		CodeGenerator.genFunc("getYear", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 10000);
		CodeGenerator.genCode("div");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void getMonth() {
		CodeGenerator.genFunc("getMonth", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 10000);
		CodeGenerator.genCode("mod");
		CodeGenerator.genCode("ldc", 100);
		CodeGenerator.genCode("div");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void getDay() {
		CodeGenerator.genFunc("getDay", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 100);
		CodeGenerator.genCode("mod");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void addTime() {
		CodeGenerator.genFunc("addTime", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("ldp");
		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("add");
		CodeGenerator.call("validTime");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void subTime() {
		CodeGenerator.genFunc("subTime", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("ldp");
		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("sub");
		CodeGenerator.call("validTime");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void mulTime() {
		CodeGenerator.genFunc("mulTime", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("ldp");
		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("multi");
		CodeGenerator.call("validTime");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void divTime() {
		CodeGenerator.genFunc("divTime", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("ldp");
		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("div");
		CodeGenerator.call("validTime");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void modTime() {
		CodeGenerator.genFunc("modTime", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("ldp");
		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("mod");
		CodeGenerator.call("validTime");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void validTime() {
		CodeGenerator.genFunc("validTime", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("ldc", 86400);
		CodeGenerator.genCode("add");
		CodeGenerator.genCode("ldc", 86400);
		CodeGenerator.genCode("mod");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}


	protected static void addDate() {
		// todo
	}

	protected static void subDate() {
		// todo
	}

	protected static void validDate() {
		// todo
		CodeGenerator.genFunc("validDate", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void run(String name) {
		try {
			DefinedFunction.class.getDeclaredMethod(name).invoke(null);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
