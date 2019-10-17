import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

	static int D, W, K;
	static int[][] map;
	static int[] inject;
	static int min;

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		for (int test_case = 1; test_case <= T; test_case++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new int[D][W];
			inject = new int[D];
			min = D;

			for (int i = 0; i < D; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			if (isPass(map)) {
				min = 0;
			} else {
				dfs(0, 0);
			}

			System.out.println("#" + test_case + " " + min);
		}

	}

	private static void dfs(int i, int numOfInject) {
		if (numOfInject >= min)
			return;
		
		if (i == D) {

			if (numOfInject < min) {
				int[][] temp = new int[D][W];
				int xIdx = 0;
				int yIdx = 0;
				for (int[] row : map) {
					yIdx = 0;
					for (int v : row) {
						temp[xIdx][yIdx++] = v;
					}
					xIdx++;
				}

				for (int injectIdx = 0; injectIdx < inject.length; injectIdx++) {
					if (inject[injectIdx] == 0) {
						Arrays.fill(temp[injectIdx], 0);
					} else if (inject[injectIdx] == 1) {
						Arrays.fill(temp[injectIdx], 1);
					}
				}
			
				if (isPass(temp)) {
					min = numOfInject;
				}
			}
			
			return;
		}
		
		inject[i] = -1;
		dfs(i + 1, numOfInject);
		inject[i] = 0;
		dfs(i + 1, numOfInject + 1);
		inject[i] = 1;
		dfs(i + 1, numOfInject + 1);
	}

	private static boolean isPass(int[][] temp) {
		boolean pass = true;
		for (int j = 0; j < W; j++) {
			int prev;
			int count = 0;
			int cur = temp[0][j];
			;
			for (int i = 0; i < D; i++) {
				prev = cur;
				cur = temp[i][j];
				if (prev == cur) {
					count++;
					if (count >= K) {
						break;
					}
				} else {
					count = 1;
				}
			}
			if (count < K) {
				pass = false;
				break;
			}
		}
		return pass;
	}

}
