import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {

    private class Team {
        int id;
        String name;
        int wins;
        int losses;
        int remaining;

        public Team(int id, String name, int wins, int losses, int remaining) {
            this.id = id;
            this.name = name;
            this.wins = wins;
            this.losses = losses;
            this.remaining = remaining;
        }

        String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", wins=" + wins +
                    ", losses=" + losses +
                    ", remaining=" + remaining +
                    '}';
        }
    }

    private Map<String, Team> teams;
    private int[][] against;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        teams = new LinkedHashMap<>();
        against = new int[n][n];
        for (int i = 0; i < n; i++) {
            String teamName = in.readString();
            teams.put(teamName, new Team(i, teamName, in.readInt(), in.readInt(), in.readInt()));
            for (int j = 0; j < n; j++) {
                against[i][j] = in.readInt();
            }
        }
//        System.out.println(teams);
//        System.out.println(Arrays.deepToString(against));
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        return teams.get(team).wins;
    }

    // number of losses for given team
    public int losses(String team) {
        return teams.get(team).losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return teams.get(team).remaining;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        int id1 = teams.get(team1).id;
        int id2 = teams.get(team2).id;
        return against[id1][id2];
    }

    private Team getTeamById(int id) {
        for (Team team : teams.values()) {
            if (team.id == id) {
                return team;
            }
        }
        throw new IllegalArgumentException("Invalid id");
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (teams.size() == 1) {
            // deals with cases like teams1.txt
            return false;
        }
        Team target = teams.get(team);
        // Trivial elimination.
        for (Team temp : teams.values()) {
            if (!temp.getName().equals(team) && temp.wins >= target.wins + target.remaining) {
                return true;
            }
        }

        // Nontrivial elimination: use maxflow
        FlowNetwork flowNetwork = buildFlow(team);
        new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        for (FlowEdge flowEdge : flowNetwork.adj(0)) {
            if (flowEdge.flow() < flowEdge.capacity()) {
                return true;
            }
        }
        return false;
    }

    private FlowNetwork buildFlow(String targetTeamName) {
        Team target = teams.get(targetTeamName);
        int n = numberOfTeams();
        int againstVertexNum = (n * (n - 1)) / 2;
        FlowNetwork flowNetwork = new FlowNetwork(againstVertexNum + n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int againstNum = against[i][j];
                if (againstNum > 0) {
                    int againstIndex = getAgainstIndex(i, j);
                    flowNetwork.addEdge(new FlowEdge(0, againstIndex, againstNum));
                    flowNetwork.addEdge(new FlowEdge(againstIndex, againstVertexNum + i + 1, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(againstIndex, againstVertexNum + j + 1, Double.POSITIVE_INFINITY));
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int capacity = target.wins + target.remaining - getTeamById(i).wins;
            if (capacity > 0) {
                flowNetwork.addEdge(new FlowEdge(againstVertexNum + i + 1, flowNetwork.V() - 1, capacity));
            }
        }
//        System.out.println("target:" + targetTeamName + ", FlowNetwork:" + flowNetwork);
        return flowNetwork;
    }

    private int getAgainstIndex(int i, int j) {
        int index = j - i;
        for (int k = 0; k < i; k++) {
            index += numberOfTeams() - 1 - k;
        }
        return index;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) {
            return null;
        }
        Set<String> result = new HashSet<>();
        // Trivial elimination.
        Team target = teams.get(team);
        for (Team temp : teams.values()) {
            if (!temp.getName().equals(team) && temp.wins > target.wins + target.remaining) {
                result.add(temp.name);
            }
        }
        // Nontrivial elimination: use maxflow
        FlowNetwork flowNetwork = buildFlow(team);
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, flowNetwork.V() - 1);
        int n = numberOfTeams();
        int againstVertexNum = (n * (n - 1)) / 2;
        for (int i = 0; i < n; i++) {
            if (fordFulkerson.inCut(againstVertexNum + i + 1)) {
                result.add(getTeamById(i).name);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
//        System.out.println(division.numberOfTeams());
//        System.out.println(division.teams());
//        System.out.println(division.wins("Hufflepuff"));
//        System.out.println(division.losses("Gryffindor"));
//        System.out.println(division.remaining("Ravenclaw"));
//        System.out.println(division.against("Hufflepuff", "Slytherin"));
//        System.out.println(division.isEliminated("Gryffindor"));
//        System.out.println(division.getAgainstIndex(0, 3));
//        System.out.println(division.getAgainstIndex(1, 2));
//        System.out.println(division.getAgainstIndex(2, 3));

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
