package de.femtopedia.studip.hisqis.parser;

import de.femtopedia.studip.hisqis.parsed.Account;
import de.femtopedia.studip.hisqis.parsed.Category;
import de.femtopedia.studip.hisqis.parsed.Mark;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@NoArgsConstructor
public class TableParser {

    private List<Account> accounts = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private List<Mark> marks = new ArrayList<>();
    @Getter
    private float averageGrade;
    @Getter
    private String graduation;

    public List<Category> parse(Element table) throws ParseException {
        Elements rows = table.select("tr");
        Account currAcc = null;
        Category currCat = null;
        for (Element row : rows) {
            Elements columns = row.select("td");
            switch (columns.size()) {
            case 0:
                Elements header = row.select("th");
                if (!header.hasAttr("colspan")) {
                    continue;
                }
                this.graduation = new GraduationParser().parse(header);
                break;
            case 5:
                this.averageGrade = new AverageGradeParser().parse(columns);
                break;
            case 8:
                if (currAcc != null) {
                    currAcc.getMarks().addAll(marks);
                    accounts.add(currAcc);
                    currAcc = null;
                    marks = new ArrayList<>();
                }
                if (currCat != null) {
                    currCat.getAccounts().addAll(accounts);
                    categories.add(currCat);
                }
                currCat = new CategoryParser().parse(columns);
                accounts = new ArrayList<>();
                break;
            case 9:
                String[] cells = new String[9];
                int i = 0;
                for (Element col : columns) {
                    cells[i++] = col.text();
                }
                if (!cells[3].isEmpty()) {
                    marks.add(new MarkParser().parse(cells));
                } else {
                    if (currAcc != null) {
                        currAcc.getMarks().addAll(marks);
                        accounts.add(currAcc);
                    }
                    currAcc = new AccountParser().parse(cells);
                    marks = new ArrayList<>();
                }
                break;
            default:
                break;
            }
        }
        if (currAcc != null) {
            currAcc.getMarks().addAll(marks);
            accounts.add(currAcc);
        }
        if (currCat != null) {
            currCat.getAccounts().addAll(accounts);
            categories.add(currCat);
        }
        return categories;
    }

}
