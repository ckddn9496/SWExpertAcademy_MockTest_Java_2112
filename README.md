# SWExpertAcademy_MockTest_Java_2112

## SW Expert Academy 2112. [모의 SW 역량테스트] 보호 필름

### 1. 문제설명

출처: https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5V1SYKAaUDFAWu

input으로 `D`, `W`, `K`가 들어온다. `D * W` 배열이 이어서 주어지며 주어진 배열은 `A = 0`와 `B = 1`의 값을 갖는다. 배열은 보호필름을 뜻하며, 각 세로축에서 같은 값이 `K`번 이어진다면 그 열에 안정성이 보장되며 모든 축에서 만족하면 보호필름의 안정성이 만족된다. 만족되지 않을 경우, 가로축으로 값을 `A`나 `B`로 바꾸어 줄 수 있는 약을 넣을 수 있으며 최소한의 주사를 이용하여 안정성있는 보호필름을 만드려고할 때 최소값을 출력하는 문제. 

[제약사항]

    1. 시간제한 : 최대 50개 테스트 케이스를 모두 통과하는데, C/C++/Java 모두 5초
    2. 보호 필름의 두께 D는 3이상 13이하의 정수이다. (3≤D≤13)
    3. 보호 필름의 가로크기 W는 1이상 20이하의 정수이다. (1≤W≤20)
    4. 합격기준 K는 1이상 D이하의 정수이다. (1≤K≤D)
    5. 셀이 가질 수 있는 특성은 A, B 두 개만 존재한다.

[입력]

> 첫 줄에 총 테스트 케이스의 개수 `T`가 주어진다.
> 두 번째 줄부터 `T`개의 테스트 케이스가 차례대로 주어진다.
> 각 테스트 케이스의 첫 줄에는 보호 필름의 두께 `D`, 가로크기 `W`, 합격기준 `K`가 차례로 주어진다.
> 그 다음 `D`줄에 보호 필름 단면의 정보가 주어진다. 각 줄에는 셀들의 특성 `W`개가 주어진다. (특성`A`는 `0`, 특성`B`는 `1`로 표시된다.)

[출력]

> 테스트 케이스의 개수만큼 `T`줄에 `T`개의 테스트 케이스 각각에 대한 답을 출력한다.
> 각 줄은 `#x`로 시작하고 공백을 하나 둔 다음 정답을 출력한다. (`x`는 `1`부터 시작하는 테스트 케이스의 번호이다)
> 출력해야 할 정답은 성능검사를 통과할 수 있는 약품의 최소 투입 횟수이다. 약품을 투입하지 않고도 성능검사를 통과하는 경우에는 `0`을 출력한다.

### 2. 풀이

보호필름의 정보를 담은 이차원배열과 DFS를 이용하여 `A`와 `B`주사 혹은 넣지않는 세가지의 경우를 `N`번 조합하여 경우를 만들었다. DFS로 만들어진 주사정보에 대해 몇번의 주사가 필요한지 계산한다. 이미 안정성을 만족하는 최소 주사횟수 `min`이 있다면 `min`보다 작을 경우 이차원배열에 주사정보를 적용하고 안정성을 체크한다. 안정성이 만족되면 `min`을 갱신한다.

```java


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


```
