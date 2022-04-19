package com.task.serviceimpl;

import com.task.exception.EmptyFileException;
import com.task.exception.InvalidFileTypeException;
import com.task.exception.ReadFileException;
import com.task.exception.WriteFileException;
import com.task.model.Result;
import com.task.service.ProcessFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProcessFileServiceImpl implements ProcessFileService {
    /**
     * This method processes file and return the expected output
     *
     * @param inputFile
     * @return
     * @throws IOException
     */
    @Override
    public List<String> processFile(final MultipartFile inputFile) throws IOException {
        validateFileType(inputFile);
        List<String> allWords = getAllWordsFromFile(inputFile);
        if (Objects.isNull(allWords) || allWords.isEmpty()) {
            throw new EmptyFileException("Provided file does not contains any words");
        }
        List<Result> result = new ArrayList<>();
        allWords.forEach(x -> calculateVowels(x, result));
        List<String> generatedResult = generateFinalResult(result);
        storeResultInFile(generatedResult);
        return generatedResult;
    }

    /**
     * this method validates the file type if the file is .txt
     *
     * @param inputFile
     */
    private void validateFileType(MultipartFile inputFile) {
        if (!(inputFile.getContentType().equalsIgnoreCase("text/plain"))) {
            throw new InvalidFileTypeException("Please select .txt files");
        }
    }

    /**
     * this method stores result in output.txt under resources directory
     *
     * @param generatedResult
     * @throws IOException
     */
    private void storeResultInFile(List<String> generatedResult) throws IOException {

        try (FileWriter fileWriter = new FileWriter("src/main/resources/output.txt")) {
            generatedResult.forEach(x -> {
                try {
                    fileWriter.write(x);
                    fileWriter.write(System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            throw new WriteFileException("Error while writing the result in file");
        }
    }

    /**
     * this method converts result to the format expected
     *
     * @param result
     * @return
     */
    private List<String> generateFinalResult(List<Result> result) {
        List<String> generatedResult = new ArrayList<>();
        result.forEach(x -> {
            BigDecimal average = new BigDecimal((double) x.getCount() / (double) x.getTimes());
            String finalResult = "({" + String.join(", ", x.getVowels()) + "}, " + x.getLength() + ") -> " + average;
            generatedResult.add(finalResult);
        });
        return generatedResult;
    }

    /**
     * this method calculates the vowels in each word
     *
     * @param s
     * @param result
     */
    private void calculateVowels(String s, List<Result> result) {
        Result newResult = new Result();
        Set<String> vowels = new HashSet<>();
        int count = 0;
        for (Character c : s.toCharArray()) {
            switch (c) {
                case 'a':
                    vowels.add("a");
                    count++;
                    break;
                case 'o':
                    vowels.add("o");
                    count++;
                    break;
                case 'e':
                    vowels.add("e");
                    count++;
                    break;
                case 'i':
                    vowels.add("i");
                    count++;
                    break;
                case 'u':
                    vowels.add("u");
                    count++;
                    break;
            }
        }
        String checkString = s.replaceAll("[^a-zA-Z]", "");
        if (checkVowelsInResult(result, vowels, checkString.length(), count)) {
            newResult.setVowels(vowels.stream().toList());
            newResult.setCount(count);
            newResult.setTimes(1);
            newResult.setLength(checkString.length());
            result.add(newResult);
        }
    }


    /**
     * this method verifies if the same vowels present in the result with same length and replaces it
     *
     * @param result
     * @param vowels
     * @param length
     * @param count
     * @return
     */
    private boolean checkVowelsInResult(List<Result> result, Set<String> vowels, int length, int count) {
        boolean check = true;
        for (Result updateResult : result) {
            if (Arrays.deepEquals(updateResult.getVowels().toArray(), vowels.toArray()) && updateResult.getLength() == length) {
                updateResult.setTimes(updateResult.getTimes() + 1);
                updateResult.setCount(updateResult.getCount() + count);
                check = false;
            }
        }
        return check;
    }

    /**
     * this method scans the file and take out all words int the list
     *
     * @param inputFile
     * @return
     */
    private List<String> getAllWordsFromFile(final MultipartFile inputFile) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputFile.getInputStream()))) {
            List<String> lines = new ArrayList<>();
            bufferedReader.lines().forEach(lines::add);
            List<String> words = new ArrayList<>();
            lines.forEach(x -> {
                StringTokenizer stringTokenizer = new StringTokenizer(x, " ");
                while (stringTokenizer.hasMoreTokens()) {
                    words.add(stringTokenizer.nextToken().toLowerCase(Locale.ROOT));
                }
            });
            return words;
        } catch (IOException e) {
            throw new ReadFileException("Error while reading file");
        }
    }
}
