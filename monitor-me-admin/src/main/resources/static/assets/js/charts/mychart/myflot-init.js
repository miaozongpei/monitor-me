
function RealTimeLineChart(elementId,color) {
    return{
        color:color,
        elementId:elementId,
        init:function (data) {
            $.plot("#realtime-"+elementId, [data], {
                yaxis: {
                    min: 0,
                    max:3000
                },
                xaxis: {
                    mode: "time",
                    tickLength: 10
                },
                colors: [color],
                series: {
                    lines: {
                        lineWidth: 0,
                        fill: true,
                        fillColor: {
                            colors: [{
                                opacity: 1
                            }, {
                                opacity: 1
                            }]
                        },
                        steps: false
                    },
                    shadowSize: 0
                },
                grid: {
                    hoverable: true,
                    clickable: false,
                    borderWidth: 0,
                    aboveData: false
                }
            }).draw()
        }
    }
}