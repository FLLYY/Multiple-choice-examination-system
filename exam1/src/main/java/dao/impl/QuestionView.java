package dao.impl;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;


public class QuestionView extends VBox {
    private Integer orderNum;
    private Question question;
    private ToggleGroup optionsGroup;

    public QuestionView(Integer orderNum, Question question) {
        this.orderNum = orderNum;
        this.question = question;

        // 初始化题目视图
        initQuestionView();
    }

    private void initQuestionView() {
        // 创建题干标签
        String fullText = orderNum + "、" + question.getTitle()+"( )";
        List<String> lines = splitText(fullText, 14);

        for (String line : lines) {
            Label titleLabel = new Label(line);
            titleLabel.setFont(new Font(48));
            titleLabel.setStyle("-fx-text-fill: white;");
            getChildren().add(titleLabel);
        }

        optionsGroup = new ToggleGroup();

        // 创建选项单选按钮
        String[] options = question.getOption();
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(options[i]);
            radioButton.setUserData(orderNum);  // 将题号信息存储在用户数据中
            radioButton.setFont(new Font(32));
            radioButton.setStyle("-fx-text-fill: white;");
            radioButton.setToggleGroup(optionsGroup);
            getChildren().add(radioButton);
        }
    }

    private List<String> splitText(String text, int maxCharsPerLine) {
        List<String> lines = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += maxCharsPerLine) {
            int endIndex = Math.min(i + maxCharsPerLine, length);
            lines.add(text.substring(i, endIndex));
        }

        return lines;
    }

    public String getSelectedAnswer() {
        RadioButton selectedRadioButton = (RadioButton) optionsGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            Integer selectedOrderNum = (Integer) selectedRadioButton.getUserData();
            // 使用 selectedOrderNum 进行适当的处理，或者将其作为答案的一部分返回
            return selectedRadioButton.getText();
        } else {
            return null;
        }
    }

}
