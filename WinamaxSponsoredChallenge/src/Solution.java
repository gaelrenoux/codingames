import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse the standard input
 * according to the problem statement.
 **/
class Solution {

	public static void main(String args[]) {
		try (Scanner in = new Scanner(System.in)) {

			int fitzCardsCount = in.nextInt();
			LinkedList<Integer> fitzCards = new LinkedList<>();
			for (int i = 0; i < fitzCardsCount; i++) {
				fitzCards.offerLast(valueOf(in.next()));
			}

			int simmonsCardsCount = in.nextInt();
			LinkedList<Integer> simmonsCards = new LinkedList<>();
			for (int i = 0; i < simmonsCardsCount; i++) {
				simmonsCards.offerLast(valueOf(in.next()));
			}

			System.err.println(fitzCards);
			System.err.println(simmonsCards);

			LinkedList<Integer> accumulatedFitzCards = new LinkedList<>();
			LinkedList<Integer> accumulatedSimmonsCards = new LinkedList<>();

			int roundCount = 0;

			while (!fitzCards.isEmpty() && !simmonsCards.isEmpty()) {

				Integer fitzCard = fitzCards.poll();
				Integer simmonsCard = simmonsCards.poll();

				accumulatedFitzCards.offerLast(fitzCard);
				accumulatedSimmonsCards.offerLast(simmonsCard);

				if (fitzCard > simmonsCard) {
					roundCount = roundCount + 1;
					fitzCards.addAll(accumulatedFitzCards);
					fitzCards.addAll(accumulatedSimmonsCards);
					accumulatedFitzCards.clear();
					accumulatedSimmonsCards.clear();
				} else if (fitzCard < simmonsCard) {
					roundCount = roundCount + 1;
					simmonsCards.addAll(accumulatedFitzCards);
					simmonsCards.addAll(accumulatedSimmonsCards);
					accumulatedFitzCards.clear();
					accumulatedSimmonsCards.clear();
				} else {
					if (fitzCards.size() < 4 || simmonsCards.size() < 4) {
						System.out.println("PAT");
						return;
					}
					accumulatedFitzCards.offerLast(fitzCards.poll());
					accumulatedFitzCards.offerLast(fitzCards.poll());
					accumulatedFitzCards.offerLast(fitzCards.poll());
					accumulatedSimmonsCards.offerLast(simmonsCards.poll());
					accumulatedSimmonsCards.offerLast(simmonsCards.poll());
					accumulatedSimmonsCards.offerLast(simmonsCards.poll());
				}

				System.err.println(roundCount + " : " + fitzCard + " vs "
						+ simmonsCard);
				//System.err.println(fitzCards);
				//System.err.println(simmonsCards);
			}

			if (fitzCards.isEmpty()) {
				System.out.println("2 " + roundCount);
			} else {
				System.out.println("1 " + roundCount);
			}
		}
	}

	public static Integer valueOf(String card) {
		String value = card.substring(0, card.length() - 1);
		if ("A".equals(value)) {
			return 20;
		} else if ("K".equals(value)) {
			return 19;
		} else if ("Q".equals(value)) {
			return 18;
		} else if ("J".equals(value)) {
			return 17;
		} else {
			return Integer.parseInt(value);
		}
	}
}