package com.m.beyond.view.page.buttons;

import com.m.beyond.view.page.AbstractElement;
import com.m.beyond.view.page.functions.AbstractFunction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Button extends AbstractElement {
    private BtnSizeEnum btnSize = BtnSizeEnum.Default;
    private BtnTypeEnum btnType = BtnTypeEnum.Default;
    private BtnColorEnum btnColor = BtnColorEnum.Primary;
    private String label;
    private AbstractFunction onClientFunction;
    private String floatType;


    private String onClientFunctionName;
    private String onClientFunctionScript;

    public String getBtnSize() {
        return btnSize.value;
    }

    public String getBtnType() {
        return btnType.value;
    }

    public String getBtnColor() {
        return btnColor.value;
    }

    public Button(String label, AbstractFunction onClientFunction) {
        this.label = label;
        this.onClientFunction = onClientFunction;
        this.onClientFunctionName = onClientFunction.getClass().getSimpleName()+"()";
        this.onClientFunctionName = onClientFunction.toHtml();
    }

    public Button(String label, String onClientFunctionName) {
        this.label = label;
        this.onClientFunctionName = onClientFunctionName;
    }

    public enum BtnColorEnum {
        /**
         * 默认 普通按钮
         */
        Default("btn-default"),
        Primary("btn-primary"),
        Blue("btn-blue"),
        Yellow("btn-yellow"),
        Darkorange("btn-darkorange"),
        Palegreen("btn-palegreen"),
        ;
        private String value;

        BtnColorEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum BtnTypeEnum {
        /**
         * 默认 普通按钮
         */
        Default(""),
        /**
         * 圆形按钮
         */
        Circle("btn-circle");
        private String value;

        BtnTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum BtnSizeEnum {
        /**
         *
         */
        Default(""),
        Large("btn-lg"),
        Small("btn-sm"),
        Mini("btn-xs"),
        ;
        private String value;

        BtnSizeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}