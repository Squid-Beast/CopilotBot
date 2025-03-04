app.controller("ChatController", function ($scope, $sce) {
    $scope.showChat = false;

    $scope.toggleChat = function () {
        let botBaseUrl = "https://copilotstudio.microsoft.com/environments/Default-70de1992-07c6-480f-a318-a1afcba03983/bots/cre88_stockMarketHelpService/webchat";
        let uniqueUrl = botBaseUrl + "?__version__=2&t=" + new Date().getTime();
        $scope.trustedBotUrl = $sce.trustAsResourceUrl(uniqueUrl);

        $scope.showChat = !$scope.showChat;
    };

    // Initialize the chat URL on page load
    let botBaseUrl = "https://copilotstudio.microsoft.com/environments/Default-70de1992-07c6-480f-a318-a1afcba03983/bots/cre88_stockMarketHelpService/webchat";
    let uniqueUrl = botBaseUrl + "?__version__=2&t=" + new Date().getTime();
    $scope.trustedBotUrl = $sce.trustAsResourceUrl(uniqueUrl);
});