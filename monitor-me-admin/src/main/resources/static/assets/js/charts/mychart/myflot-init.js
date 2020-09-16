
function RealTimeLineChart(elementId,color) {
    return{
        color:color,
        elementId:elementId,
        init:function (data) {
            $.plot("#realtime-"+elementId, [data], {
                yaxis: {
                    min: 0
                },
                xaxis: {
                    mode: "time",
                    timezone:"browser",
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
                tooltip:true,
                tooltipOpts: {
                    defaultTheme: false,
                    dateFormat: "%Y-%m-%d %H:%M:%S",
                    content: "<span>%x</span> : <span>%y</span>",
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


function RealTimeVisitorsChart(elementId,color,minId,maxId) {
    return {
        color:color,
        elementId:elementId,
        init: function (data) {
            var options = {
                xaxis: {
                    mode: "time",
                    timezone:"browser",
                    tickLength: 5,
                    color: gridbordercolor
                },
                selection: {
                    mode: "x"
                },
                grid: {
                    hoverable: true,
                    clickable: false,
                    borderWidth: 0,
                    aboveData: false
                },
                tooltip:true,
                tooltipOpts: {
                    defaultTheme: false,
                    dateFormat: "%Y-%m-%d %H:%M",
                    content: "<span>%x</span> : <span>%y</span>",
                },
                series: {
                    lines: {
                        show: true,
                        lineWidth: 1,
                        fill: true,
                        fillColor: {
                            colors: [{
                                opacity: 0.1
                            }, {
                                opacity: 0.15
                            }]
                        }
                    },
                    shadowSize: 0
                }
            };
            var plot = $.plot("#visitors-chart-"+elementId, [{data: data, color: color}], options);
            var overview = $.plot("#visitors-chart-overview-"+elementId, [{data: data, color: color}], {
                series: {
                    lines: {
                        show: true,
                        lineWidth: 1
                    },
                    shadowSize: 0
                },
                xaxis: {
                    ticks: [],
                    timezone:"browser",
                    mode: "time"
                },
                yaxis: {
                    ticks: [],
                    min: 0,
                    autoscaleMargin: 0.1
                },
                selection: {
                    mode: "x"
                },
                grid: {
                    borderWidth: 0,
                    aboveData: false
                }
            });
            $("#visitors-chart-"+elementId).bind("plotselected", function (event, ranges) {
                //$("[id='visitors-chart-ranges-min-"+minId+"']").val(ranges.xaxis.from+"-"+new Date().getTime());
                //$("[id='visitors-chart-ranges-max-"+maxId+"']").val(ranges.xaxis.to+"-"+new Date().getTime());
                plot = $.plot("#visitors-chart-"+elementId, [{ data: data}], $.extend(true, {}, options, {
                    xaxis: {
                        min: ranges.xaxis.from,
                        max: ranges.xaxis.to
                    }
                }));
                overview.setSelection(ranges, true);
            });
            $("#visitors-chart-overview-"+elementId).bind("plotselected", function (event, ranges) {
                plot.setSelection(ranges);
                //$("[id='warning-"+host+"']")
                //$("[id='visitors-chart-ranges-min-"+minId+"']").val(ranges.xaxis.from+"-"+new Date().getTime());
                //$("[id='visitors-chart-ranges-max-"+maxId+"']").val(ranges.xaxis.to+"-"+new Date().getTime());
            });
        }
    };
};
