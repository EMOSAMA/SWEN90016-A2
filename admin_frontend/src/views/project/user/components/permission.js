const permissions = [
  {
    id: 1,
    cnName: '城市GIS',
    alias: '城市GIS',
    enName: 'CITY-GIS',
    checked: true,
    children: []
  },
  {
    id: 2,
    cnName: '城市全景图',
    alias: '城市全景图',
    enName: 'CITY-PANORAMAGRAM',
    checked: true,
    children: []
  },
  {
    id: 3,
    cnName: '城市云档案',
    alias: '城市云档案',
    enName: 'CITY-ARCHIVE',
    checked: true,
    children: [
      {
        id: 1,
        cnName: '问题档案',
        alias: '问题档案',
        enName: 'PROBLEM-ARCHIVE',
        checked: true
      },
      {
        id: 2,
        cnName: '变迁档案',
        alias: '变迁档案',
        enName: 'CITY-CHANGE-ARCHIVE',
        checked: true
      },
      {
        id: 3,
        cnName: '任务结论文档',
        alias: '任务结论文档',
        enName: 'TASK-CONCLUSION-ARCHIVE',
        checked: true
      }
    ]
  },
  {
    id: 4,
    cnName: '城市飞行任务',
    alias: '城市飞行任务',
    enName: 'CITY-FLY-SERVICE',
    checked: true,
    children: []
  },
  {
    id: 5,
    cnName: '城市智慧应用',
    alias: '城市智慧应用',
    enName: 'CITY-INTELLIGENT-APPLY',
    checked: true,
    children: [
      {
        id: 1,
        cnName: '城市变化分析',
        alias: '城市变化分析',
        enName: 'CITY-CHANGE-ANALYSIS',
        checked: true
      },
      {
        id: 2,
        cnName: '平安城市应用',
        alias: '平安城市应用',
        enName: 'CITY-SECURITY-APPLY',
        checked: true
      },
      {
        id: 3,
        cnName: '城市空间测量',
        alias: '城市空间测量',
        enName: 'CITY-SPATIAL-MEASUREMENT',
        checked: true
      },
      {
        id: 4,
        cnName: '城市智慧交通',
        alias: '城市智慧交通',
        enName: 'ITY-INTELLIGENT-TRAFFIC',
        checked: true
      }
    ]
  }
]

export default permissions
