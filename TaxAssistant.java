import java.util.*;

class TaxAssistant {
    private static final TreeMap<Integer, Double> taxBrackets = new TreeMap<>();
    private static final HashMap<String, Integer> deductions = new HashMap<>();

    static {
        taxBrackets.put(10000, 0.10);
        taxBrackets.put(40000, 0.20);
        taxBrackets.put(80000, 0.30);
        taxBrackets.put(150000, 0.40);

        deductions.put("Medical", 5000);
        deductions.put("Education", 2000);
        deductions.put("Retirement", 3000);
    }

    public static double calculateTax(int income) {
        double tax = 0.0;
        int prevLimit = 0;

        for (Map.Entry<Integer, Double> entry : taxBrackets.entrySet()) {
            int limit = entry.getKey();
            double rate = entry.getValue();

            if (income > prevLimit) {
                int taxableIncome = Math.min(income, limit) - prevLimit;
                tax += taxableIncome * rate;
                prevLimit = limit;
            } else {
                break;
            }
        }
        return tax;
    }

    public static int applyDeductions(int income, List<String> selectedDeductions) {
        int totalDeduction = 0;
        for (String deduction : selectedDeductions) {
            totalDeduction += deductions.getOrDefault(deduction, 0);
        }
        return Math.max(0, income - totalDeduction);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your income: ");
        int income = scanner.nextInt();
        
        System.out.println("Available deductions: " + deductions.keySet());
        System.out.print("Enter deductions (comma-separated): ");
        scanner.nextLine();
        String[] selected = scanner.nextLine().split(",");
        List<String> selectedDeductions = new ArrayList<>();
        for (String d : selected) {
            selectedDeductions.add(d.trim());
        }

        int netIncome = applyDeductions(income, selectedDeductions);
        double tax = calculateTax(netIncome);
        
        System.out.println("--------------------------------");
        System.out.println("Original Income: " + income);
        System.out.println("Deductions Applied: " + selectedDeductions);
        System.out.println("Net Taxable Income: " + netIncome);
        System.out.println("Total Tax: " + tax);
    }
}
