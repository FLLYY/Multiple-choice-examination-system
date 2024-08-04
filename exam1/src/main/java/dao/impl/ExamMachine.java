package dao.impl;

import dao.examdao;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.util.*;

public class ExamMachine
{
    private static Set<Question> questionRepository = new HashSet<>();//总的题库
    private List<QuestionView> questionViews = new ArrayList<>();
    public ArrayList<Question> paper; // 将paper声明为成员变量
    private Integer currentPage =0;
    private BorderPane layout;
    private Label clockLabel;

    public ExamMachine(BorderPane layout)
    {
        System.out.println("系统正在启动，请稍等····");
        System.out.println("正在加载题库····");
        this.layout=layout;
        load();
        System.out.println("系统初始化完毕，您可以登录了");
    }



    // 从数据库中获取题目
    void load()
    {
        examdao em = new ExamDAOImpl();
        questionRepository = em.questionRepository();

    }

    //可以指定出几道题目
    public static ArrayList<Question> generateRandomizedPaper(int num)
    {
        System.out.println("正在生成随机试卷...");
        final List<Question> questionRepository = new ArrayList<>(ExamMachine.questionRepository);
        Collections.shuffle(questionRepository);
        ArrayList<Question> finalPaper = new ArrayList<>();
        Random random = new Random();

        for (Question question : questionRepository)
        {
            String[] oldOptions = question.getOption();
            String[] newOptions = new String[oldOptions.length];
            char optionOrder = 'A';

            // 创建一个随机索引数组，用于重新排列选项
            int[] randomIndices = new int[oldOptions.length];
            for (int i = 0; i < oldOptions.length; i++)
            {
                randomIndices[i] = i;
            }

            for (int i = 0; i < oldOptions.length; i++)
            {
                int randomIndex = random.nextInt(oldOptions.length - i) + i;

                int temp = randomIndices[i];
                randomIndices[i] = randomIndices[randomIndex];
                randomIndices[randomIndex] = temp;

                newOptions[i] = optionOrder + "." + oldOptions[randomIndices[i]];

                if (oldOptions[randomIndices[i]].equals(question.getAnswer()))
                {
                    question.setAnswer(newOptions[i]);
                }
                optionOrder++;
            }
            question.setOption(newOptions);
            finalPaper.add(question);

            if (finalPaper.size() >= num)
            {
                break; // 填充足够的题目后退出循环
            }
        }
        for (Question question : finalPaper) {
            System.out.println("题干: " + question.getTitle());
            System.out.println("答案: " + question.getAnswer());
        }
        return finalPaper;
    }

    public void createExamScene() {

        VBox examBox = new VBox(10);
        examBox.setStyle("-fx-alignment: center; -fx-padding: 20;");
        File file = new File("考试背景.jpg");
        Image image1=new Image(file.toURI().toString(),800,500,false,false);
        BackgroundImage backgroundImg = new BackgroundImage(
                image1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        //创建一个背景对象
        Background background = new Background(backgroundImg);
        Label numQuestionsLabel = new Label("选择题目数量:");
        numQuestionsLabel.setStyle("-fx-font-size: 24px;-fx-text-fill: white;");
        TextField numQuestionsInput = new TextField();
        numQuestionsInput.setStyle("-fx-pref-width: 50px; -fx-pref-height: 30px;");
        numQuestionsInput.setMaxWidth(50);
        numQuestionsInput.setText("5"); // 设置默认文本

        Button startExamButton = new Button("开始考试");
        startExamButton.setPrefWidth(100); // 设置按钮宽度为 70
        startExamButton.setPrefHeight(20);
        startExamButton.setOnAction(e ->
        {
            paper=ExamMachine.generateRandomizedPaper(Integer.parseInt(numQuestionsInput.getText()));
            createExamVisual(paper);
        });

        layout.setBackground(background);
        examBox.getChildren().addAll(numQuestionsLabel, numQuestionsInput, startExamButton);
        layout.setCenter(examBox);
    }

    private void createExamVisual(ArrayList<Question> paper) {
        layout.getChildren().clear();
        questionViews.clear(); // 清空之前的题目视图

        BorderPane examBox = new BorderPane();
        examBox.setStyle("-fx-alignment: center; -fx-padding: 20;");
        for (int i = 0; i < paper.size(); i++) {
            Question question = paper.get(i);
            QuestionView questionView = new QuestionView(i + 1, question);

            // 添加问题视图到列表
            questionViews.add(questionView);
        }
        // 显示当前页的题目
        clockLabel = DigitalClock.startDigitalClock(this);
        showQuestionPage(examBox, paper);

        layout.setCenter(examBox);
    }

    // 显示当前页的题目
    private void showQuestionPage(BorderPane examBox, ArrayList<Question> paper) {

        VBox questionVBox = new VBox(10);
        questionVBox.setStyle("-fx-alignment:top-center;-fx-text-fill: white;");

        QuestionView questionView = questionViews.get(currentPage);
        questionVBox.getChildren().add(questionView);

        Button nextButton = new Button("下一题");
        nextButton.setOnAction(e -> {
            if (currentPage < paper.size() - 1) {
                currentPage++;
                showQuestionPage(examBox, paper);
            }
        });

        Button prevButton = new Button("上一题");
        prevButton.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                showQuestionPage(examBox, paper);
            }
        });

        HBox navigationBox = new HBox(10);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.getChildren().addAll(prevButton, nextButton);

        if (currentPage == 0) {
            prevButton.setDisable(true);
        }

        if (currentPage == paper.size() - 1) {
            nextButton.setText("提交答案");
            nextButton.setOnAction(e -> handleSubmitAnswers(paper));
        }

        Label progressLabel = new Label("第 " + (currentPage + 1) + " 题 / 共 " + paper.size() + " 题");
        progressLabel.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;-fx-text-fill: white;");



        // 添加进度指示器和导航按钮
        examBox.setTop(progressLabel);
        examBox.setRight(clockLabel);
        examBox.setCenter(questionVBox);
        examBox.setBottom(navigationBox);
    }

    void handleSubmitAnswers(ArrayList<Question> paper) {
        String[] studentAnswers = new String[paper.size()];
        String[] firstChars = new String[paper.size()]; // 用于存储每个答案的第一个字符

        // 打印 paper 的内容
        for (Question question : paper) {
            System.out.println("题干: " + question.getTitle());
            System.out.println("答案: " + question.getAnswer());
        }

        for (int i = 0; i < questionViews.size(); i++) {
            QuestionView questionView = questionViews.get(i);
            if (questionView != null) {
                String selectedAnswer = questionView.getSelectedAnswer();
                studentAnswers[i] = selectedAnswer != null ? selectedAnswer : "未选择答案";

                // 提取第一个字符
                if (selectedAnswer != null && !selectedAnswer.isEmpty()) {
                    firstChars[i] = String.valueOf(selectedAnswer.charAt(0));
                }
            } else {
                studentAnswers[i] = "未选择答案";
                firstChars[i] = String.valueOf(' '); // 空字符或其他默认值
            }
        }

        // 打印 studentAnswers 的内容
        for (int i = 0; i < studentAnswers.length; i++) {
            System.out.println("学生答案 " + (i + 1) + ": " + firstChars[i]);
        }
        // 批改答案

        float score = Teacher.checkPaper(paper, firstChars);
        System.out.println(score + "1e1");
        // 显示批改结果
        // 调用新的方法显示成绩
        createResultScene(score);
    }

    private void createResultScene(float score) {
        layout.getChildren().clear();

        VBox resultBox = new VBox(10);
        resultBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Label resultLabel = new Label("最终成绩为: " + score + " 分 (满分 100 分)");
        resultLabel.setStyle("-fx-font-size: 18pt; -fx-font-weight: bold; -fx-text-fill: red;");

        resultBox.getChildren().add(resultLabel);

        layout.setCenter(resultBox);
    }
}


