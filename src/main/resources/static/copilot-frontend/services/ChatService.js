app.service('ChatService', function($http) {
    this.getBotUrl = function() {
        return $http.get("http://localhost:8080/copilot/getBotUrl"); // Backend API
    };
});