package CodeGenerator;

import Semantic.FunctionInfo;
import Semantic.FunctionSet;
import Syntax.FunctionDeclaration;
import Syntax.ParamDeclaration;
import Syntax.Statements;
import Syntax.Type;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DefinedFunction {
	private static final ParamDeclaration intParam = new ParamDeclaration(Type.INT, "dummy");
	private static final ParamDeclaration floatParam = new ParamDeclaration(Type.FLOAT, "dummy");
	private static final ParamDeclaration boolParam = new ParamDeclaration(Type.BOOL, "dummy");
	private static final ParamDeclaration charParam = new ParamDeclaration(Type.CHAR, "dummy");
	private static final ParamDeclaration dateParam = new ParamDeclaration(Type.DATE, "dummy");
	private static final ParamDeclaration timeParam = new ParamDeclaration(Type.TIME, "dummy");

	private static FunctionSet functionSet;
	private static HashMap<String, ArrayList<FunctionInfo>> overloadMap;

	private static final String[] UCodeFunction = {
			"write", "read", "lf", "addFloat", "subFloat",
			"mulFloat", "divFloat", "modFloat", "negFloat", "F2I", "I2F",
			"writeF", "writeC", "writeT", "writeD"
	};

	private static final String[] customFunc = {
			"getHour", "getMin", "getSec", "getYear", "getMonth", "getDay",
			"addTime", "subTime", "mulTime", "divTime", "modTime", "secToTime",
			"addDate", "subDate",
			"validTime", "daysToDate"
	};

	public static final HashSet<String> predefinedFunc = new HashSet<>(Arrays.asList(customFunc));

	public static void defineFunc(FunctionSet functionSet, HashMap<String, ArrayList<FunctionInfo>> overloadMap) {
		DefinedFunction.functionSet = functionSet;
		DefinedFunction.overloadMap = overloadMap;

		createFunc(Type.VOID, "write", intParam);
		createFunc(Type.VOID, "write", boolParam);
		createFunc(Type.VOID, "write", charParam);
		createFunc(Type.VOID, "write", dateParam);
		createFunc(Type.VOID, "write", timeParam);
		createFunc(Type.VOID, "write", floatParam);
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
		createFunc(Type.TIME, "secToTime", intParam);

		createFunc(Type.DATE, "addDate", dateParam, dateParam);
		createFunc(Type.DATE, "subDate", dateParam, dateParam);
		createFunc(Type.DATE, "daysToDate", intParam);
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
		CodeGenerator.genCode("mult");
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

	protected static void secToTime() {
		CodeGenerator.genFunc("secToTime", 1, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);

		CodeGenerator.genCode("lod", 2, 1);

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
		CodeGenerator.genFunc("addDate", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("add");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void subDate() {
		// todo
		CodeGenerator.genFunc("subDate", 2, 2, 2);
		CodeGenerator.genCode("sym", 2, 1, 1);
		CodeGenerator.genCode("sym", 2, 2, 1);

		CodeGenerator.genCode("lod", 2, 1);
		CodeGenerator.genCode("lod", 2, 2);
		CodeGenerator.genCode("sub");

		CodeGenerator.genCode("retv");
		CodeGenerator.genCode("end");
	}

	protected static void daysToDate() {
		// todo 확인
		CodeGenerator.genFunc("daysToDate", 1, 2, 2);
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
