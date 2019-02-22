package autre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author jmoreno
 */
public class PageRank {

	public int path[][];
	public double pagerank[];
	public int totalNodes;

	public PageRank(int totalNodes) {
		this.path = new int[totalNodes][totalNodes];
		this.pagerank = new double[totalNodes];
		this.totalNodes = totalNodes;
	}

	public void calc() {

		double InitialPageRank;
		double OutgoingLinks = 0;
		double DampingFactor = 0.85;
		double TempPageRank[] = new double[totalNodes];

		int ExternalNodeNumber;
		int InternalNodeNumber;
		int k;
		int iter = 1;

		InitialPageRank = 1 / (float) totalNodes;
		System.out.printf(" Total Number of Nodes :" + totalNodes + "\t Initial PageRank  of All Nodes :"
				+ InitialPageRank + "\n");

		// init
		for (k = 0; k < totalNodes; k++) {
			this.pagerank[k] = InitialPageRank;
		}

		System.out.printf("\n Initial PageRank Values , 0th Step \n");
		for (k = 0; k < totalNodes; k++) {
			System.out.printf(" Page Rank of " + k + " is :\t" + this.pagerank[k] + "\n");
		}

		while (iter <= 2) // Iterations
		{
			// Store the PageRank for All Nodes in Temporary Array
			for (k = 0; k < totalNodes; k++) {
				TempPageRank[k] = this.pagerank[k];
				this.pagerank[k] = 0;
			}

			for (InternalNodeNumber = 0; InternalNodeNumber < totalNodes; InternalNodeNumber++) {
				for (ExternalNodeNumber = 0; ExternalNodeNumber < totalNodes; ExternalNodeNumber++) {
					if (this.path[ExternalNodeNumber][InternalNodeNumber] == 1) {
						k = 0;
						OutgoingLinks = 0; // Count the Number of Outgoing Links for each ExternalNodeNumber
						while (k < totalNodes) {
							if (this.path[ExternalNodeNumber][k] == 1) {
								OutgoingLinks = OutgoingLinks + 1; // Counter for Outgoing Links
							}
							k = k + 1;
						}
						// Calculate PageRank
						this.pagerank[InternalNodeNumber] += TempPageRank[ExternalNodeNumber] * (1 / OutgoingLinks);
					}
				}
			}

			System.out.printf("\n After " + iter + "th Step \n");

			for (k = 0; k < totalNodes; k++) {
				System.out.printf(" Page Rank of " + k + " is :\t" + this.pagerank[k] + "\n");
			}

			iter = iter + 1;
		}

		// Add the Damping Factor to PageRank
		for (k = 0; k < totalNodes; k++) {
			this.pagerank[k] = (1 - DampingFactor) + DampingFactor * this.pagerank[k];
		}

		// Display PageRank
		System.out.printf("\n Final Page Rank : \n");
		for (k = 0; k < totalNodes; k++) {
			System.out.printf(" Page Rank of " + k + " is :\t" + this.pagerank[k] + "\n");
		}

	}

	int[][] lireMat(String path) throws IOException {
		int[][] ret = new int[totalNodes][totalNodes];
		try {
			File ficMat = new File(path);
			Scanner scan = new Scanner(ficMat);
			while (scan.hasNext()) {
				String[] l = scan.nextLine().split("\t");
				ret[Integer.parseInt(l[0])][Integer.parseInt(l[1])] = 1;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String args[]) {
		System.out.println("Enter the Number of WebPages \n");
		int nodes = 100;// in.nextInt();
		PageRank p = new PageRank(nodes);
		System.out.println("Enter the Adjacency Matrix with 1->PATH & 0->NO PATH Between two WebPages: \n");
		try {
			p.path = p.lireMat("matrix/AdjacencyMatrix.100.tsv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.calc();
	}

}
