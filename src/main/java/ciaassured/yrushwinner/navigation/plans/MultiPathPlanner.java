package ciaassured.yrushwinner.navigation.plans;

import ciaassured.yrushwinner.navigation.goals.PathGoal;
import jakarta.inject.Inject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MultiPathPlanner implements PathPlanner {

    @Inject
    public MultiPathPlanner() { }

    // All positions reachable in a single step (≤1 block in each axis, excluding self).
    public Iterable<PathPlan> getNeighbours(PathPlan plan, PathGoal goal) {
        List<PathPlan> result = new ArrayList<>(26);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;

                    BlockPos nextBlock = plan.getPos().add(dx, dy, dz);
                    result.addAll(findNextPlans(plan, nextBlock, goal.heuristic(nextBlock)));
                }
            }
        }
        return result;
    }

    public Collection<PathPlan> findNextPlans(PathPlan plan, BlockPos dest, double heuristicTimeCost) {
        ArrayList<PathPlan> result = new ArrayList<>(1);

        ClientWorld world = MinecraftClient.getInstance().world;

        WalkPlan.makePlan(plan, dest, world, heuristicTimeCost).ifPresent(result::add);
        JumpOnePlan.makePlan(plan, dest, world, heuristicTimeCost).ifPresent(result::add);

        return result;
    }
}
