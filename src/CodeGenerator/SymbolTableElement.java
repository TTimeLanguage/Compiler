package CodeGenerator;


/**
 * 한 변수가 U-code의 sym에서 정의될때 필요한 모든 정보를 담고있는 정보 저장 클래스.
 *
 * @see Syntax.Program
 */
public class SymbolTableElement {
	private final int blockNum;
	private final int startAddress;
	private final int size;

	public SymbolTableElement(int blockNum, int startAddress, int size) {
		this.blockNum = blockNum;
		this.startAddress = startAddress;
		this.size = size;
	}

	public int getBlockNum() {
		return blockNum;
	}

	public int getStartAddress() {
		return startAddress;
	}

	public int getSize() {
		return size;
	}
}
