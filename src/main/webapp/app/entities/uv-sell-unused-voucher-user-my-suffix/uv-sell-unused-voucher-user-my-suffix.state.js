(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uv-sell-unused-voucher-user-my-suffix', {
            parent: 'entity',
            url: '/uv-sell-unused-voucher-user-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvSellUnusedVoucherUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-usersmySuffix.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvSellUnusedVoucherUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('uv-sell-unused-voucher-user-my-suffix-detail', {
            parent: 'uv-sell-unused-voucher-user-my-suffix',
            url: '/uv-sell-unused-voucher-user-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvSellUnusedVoucherUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-user-my-suffix-detail.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvSellUnusedVoucherUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UvSellUnusedVoucherUser', function($stateParams, UvSellUnusedVoucherUser) {
                    return UvSellUnusedVoucherUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uv-sell-unused-voucher-user-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uv-sell-unused-voucher-user-my-suffix-detail.edit', {
            parent: 'uv-sell-unused-voucher-user-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-user-my-suffix-dialog.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvSellUnusedVoucherUser', function(UvSellUnusedVoucherUser) {
                            return UvSellUnusedVoucherUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-sell-unused-voucher-user-my-suffix.new', {
            parent: 'uv-sell-unused-voucher-user-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-user-my-suffix-dialog.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userName: null,
                                password: null,
                                email: null,
                                isVerifiedUser: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uv-sell-unused-voucher-user-my-suffix', null, { reload: 'uv-sell-unused-voucher-user-my-suffix' });
                }, function() {
                    $state.go('uv-sell-unused-voucher-user-my-suffix');
                });
            }]
        })
        .state('uv-sell-unused-voucher-user-my-suffix.edit', {
            parent: 'uv-sell-unused-voucher-user-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-user-my-suffix-dialog.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvSellUnusedVoucherUser', function(UvSellUnusedVoucherUser) {
                            return UvSellUnusedVoucherUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-sell-unused-voucher-user-my-suffix', null, { reload: 'uv-sell-unused-voucher-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-sell-unused-voucher-user-my-suffix.delete', {
            parent: 'uv-sell-unused-voucher-user-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-sell-unused-voucher-user-my-suffix/uv-sell-unused-voucher-user-my-suffix-delete-dialog.html',
                    controller: 'UvSellUnusedVoucherUserMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UvSellUnusedVoucherUser', function(UvSellUnusedVoucherUser) {
                            return UvSellUnusedVoucherUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-sell-unused-voucher-user-my-suffix', null, { reload: 'uv-sell-unused-voucher-user-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
