package fi.tuni.prog3.modules;

import java.util.List;

import fi.tuni.prog3.modules.DegreeModule.Name;

public class TreeDegreeModule {
    public DegreeModule degreeModule;
    public List<TreeDegreeModule> subdegreeModule;

    public TreeDegreeModule(DegreeModule degreeModule, List<TreeDegreeModule> subdegreeModule) {
        this.degreeModule = degreeModule;
        this.subdegreeModule = subdegreeModule;
    }

    public Name getName() {
        return degreeModule.name;
    }

    public String toString() {
        String name;
        if (this.degreeModule.name.en == null) {
            name = this.degreeModule.name.fi;
            return name;
        }
        name = this.degreeModule.name.en;
        return name;
    }

    public static void printAll(TreeDegreeModule root, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(root.getName().en);
        if (root.subdegreeModule == null) {
            return;
        }
        for (TreeDegreeModule module : root.subdegreeModule) {
            printAll(module, depth + 1);
        }

        return;
    }
    
}