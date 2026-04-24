package ciaassured.yrushwinner.navigation.plans;

import ciaassured.yrushwinner.navigation.goals.PathGoal;

import java.util.Collection;

public interface PathPlanner {
    Iterable<PathPlan> getNeighbours(PathPlan plan, PathGoal goal);
}
