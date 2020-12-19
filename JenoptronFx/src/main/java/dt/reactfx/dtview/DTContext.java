/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package dt.reactfx.dtview;

import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;

public class DTContext {
	private final int cellX;
	private final int cellY;
	private final Cell cell;

	public DTContext(final int cell_x, final int cell_y, final Cell cell) {
		cellX = cell_x;
		cellY = cell_y;
		this.cell = cell;
	}

	public final ICondition getiCondition() {
		return getCell().getCondition();
	}

	public final IAction getiAction() {
		return getCell().getAction();
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public Cell getCell() {
		return cell;
	}
}
