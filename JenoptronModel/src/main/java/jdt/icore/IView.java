package jdt.icore;

public interface IView {
	int nrConditions();
	int nrActions();
	int nrRules();

	void hideAllRules();
	void showAllRules();
}
