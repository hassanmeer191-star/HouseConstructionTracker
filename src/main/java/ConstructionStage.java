public class ConstructionStage {
private int stageId;
private int projectId;
private String stageName;     //ex plumbing electrical etc
private String description;   //detailed info of work in the stage
private String status;        //ex start hwa ya ni inprogess/ Completed etc
private int stageOrder;       //sequence of stages 

public ConstructionStage(int stageId, int projectId, String stageName, String description, int stageOrder) {
this.stageId = stageId;
this.projectId = projectId;
this.stageName = stageName;
this.description = description;
this.stageOrder = stageOrder;
}


}
