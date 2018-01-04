(function() {
    'use strict';

    angular
        .module('unlimitedVoucherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uv-brand-my-suffix', {
            parent: 'entity',
            url: '/uv-brand-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvBrand.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brandsmySuffix.html',
                    controller: 'UvBrandMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvBrand');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('uv-brand-my-suffix-detail', {
            parent: 'uv-brand-my-suffix',
            url: '/uv-brand-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unlimitedVoucherApp.uvBrand.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brand-my-suffix-detail.html',
                    controller: 'UvBrandMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uvBrand');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UvBrand', function($stateParams, UvBrand) {
                    return UvBrand.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uv-brand-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uv-brand-my-suffix-detail.edit', {
            parent: 'uv-brand-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brand-my-suffix-dialog.html',
                    controller: 'UvBrandMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvBrand', function(UvBrand) {
                            return UvBrand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-brand-my-suffix.new', {
            parent: 'uv-brand-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brand-my-suffix-dialog.html',
                    controller: 'UvBrandMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                brandName: null,
                                brandCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uv-brand-my-suffix', null, { reload: 'uv-brand-my-suffix' });
                }, function() {
                    $state.go('uv-brand-my-suffix');
                });
            }]
        })
        .state('uv-brand-my-suffix.edit', {
            parent: 'uv-brand-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brand-my-suffix-dialog.html',
                    controller: 'UvBrandMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UvBrand', function(UvBrand) {
                            return UvBrand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-brand-my-suffix', null, { reload: 'uv-brand-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uv-brand-my-suffix.delete', {
            parent: 'uv-brand-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/uv-brand-my-suffix/uv-brand-my-suffix-delete-dialog.html',
                    controller: 'UvBrandMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UvBrand', function(UvBrand) {
                            return UvBrand.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uv-brand-my-suffix', null, { reload: 'uv-brand-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
