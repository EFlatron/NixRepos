package csv;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvTable{
    private final String[] header;
    private final List<String[]> body;

    public CsvTable(CsvParser parser){
        header = parser.getHeader();
        body = parser.getBody();
    }

    public String get(int row, String colName){
        int column = index(colName);
        return get(row, column);
    }

    public String get(int row, int column){
        String[] fullRow = body.get(row);
        return fullRow  [column];
    }

    private int index(String colName){
        for (int i = 0; i < header.length; i++) {
            if(header[i].equals(colName))
                return i;
        }
        throw new IllegalArgumentException("Column doesn't exist: " + colName);
    }
    
    public List<String> getHeader(){
        return Arrays.stream(header)
                .collect(Collectors.toList());

    }

    public int bodySize(){
        return body.size();
    }
}
