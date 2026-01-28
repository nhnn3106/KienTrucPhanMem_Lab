package observer;

public class TeamMember implements Observer {
    private String memberName;

    public TeamMember(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public void update(String message) {
        System.out.println("[Thông báo Slack cho " + memberName + "]: " + message);
    }
}