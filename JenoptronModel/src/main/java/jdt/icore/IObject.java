package jdt.icore;

import java.io.Serializable;

public interface IObject extends Serializable {
	IObject deepcopy();
}
