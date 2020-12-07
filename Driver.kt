import LexAnalyzerKt;
import LexParserKt;
import ExprEvaluatorKt;

/* MAIN
 * Functionality: Here's the kotculator (kotlin calculator) in its shiny and monstrously hefty form. Give it an
 * equation, watch it shriek in agony as it presents you with its only purpose for existing. Remember, the blood's on
 * your hands if you terminate it
 * Author's Comments: All that aside, we sincerely hope you enjoy testing this code as much as much as we enjoyed
 * downing fifteen cans of A&W each and screaming our lungs dry because we implemented the power parser the opposite way
 */
fun main () {
    var exp = ExprEvaluatorKt ();
    var tree = LexParserKt ();
    var analyzer = LexAnalyzerKt ();

    var continueExpr = "1"
    while (continueExpr != "0") {
        try {
            print("Please enter a mathematical equation: ")
            var str = readLine().toString()

            str = analyzer.inputStringModifier(str)
            var lexemeList = analyzer.fillLexemeList(str)
            analyzer.lexicalAnalysis(lexemeList)

            var lexTree = tree.lexemeTree(lexemeList as MutableList<String>)
            println(exp.evaluate(lexTree))

            print("Continue? (Enter 0 to stop): ")
            continueExpr = readLine().toString()
        }
        catch (e : NoSuchElementException) {
            println("No equation detected. Please specify a mathematical equation")
        }
    }

    println("Thank you!")
}
