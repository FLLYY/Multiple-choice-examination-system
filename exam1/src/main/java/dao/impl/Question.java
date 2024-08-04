package dao.impl;

public class Question {
    private Integer orderNum;//题号
    private String title;//题干
    private String[] option;//选项
    private String answer;//答案

    //不包含题号的构造方法，题号的后期生成卷子的时候赋予的
    public Question(String title, String[] option, String answer) {
        this.title = title;
        this.option = option;
        this.answer = answer;
    }
    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (obj instanceof Question){
            //首先造型
            Question anotherQue = (Question)obj;
            if(this.title.equals(anotherQue.title)){
                //题目的题干相同，则认为是同一道题，返回true
                return true;
            }
        }
        //obj不是Question类型的，那还比啥呀，肯定不相同
        return false;
    }
    @Override
    public int hashCode(){
        //返回题干字符串的 hashcode
        return this.title.hashCode();
    }
    //get和set······
    public String[] getOption()
    {
        return option;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public void setOption(String[] option)
    {
        this.option = option;
    }

    public String getTitle()
    {
        return title;
    }



}
