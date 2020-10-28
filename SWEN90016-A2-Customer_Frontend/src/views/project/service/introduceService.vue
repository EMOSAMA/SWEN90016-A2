<template>
  <div class="student">
    <div class="dialog-wrapper">
      <span style = "margin: auto">You Will Love These Services in Our Salon</span>
      <el-divider></el-divider>
      <el-carousel :interval="4000" type="card" height="200px">
        <el-carousel-item v-for="(item, index) in this.introduce" :key="index">
          <span style="font-size: large; position: absolute; top: 0; left: 0;">{{item}}</span>
          <img src="../../../assets/haircut.jpg" v-if="index == 0" class="bannerImg"/>
          <img src="../../../assets/haircolor.jpg" v-if="index == 1" class="bannerImg"/>
          <img src="../../../assets/hairwash.jpg" v-if="index == 2" class="bannerImg"/>
        </el-carousel-item>
      </el-carousel>
      <el-divider></el-divider>
      <h2 align="center">Welcome to our online salon service appointment website!</h2>
      <br>
      <h3 align="center">A lot of services like hair cut, hair color and hair wash are provided here.</h3>
      <br>
      <h3 align="center">Once you have login our website, you can book these service in 'Book Service' Section.</h3>
      <br>
      <h3 align="center">PLease enjoy yourself.</h3>
      <br>
      <h3 align="center" v-if="!isLogin">PLease click below button to login.</h3>
      <br>
      <div align="center">
        <el-button type="primary" v-if="!isLogin" @click="handleLogin">Login</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/layout/components/assets-page-sample'
  import { queryService } from 'api/serviceAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
  import store from '@/store'
  import { getCustomer } from 'api/customerAPI'
  import { queryBiller } from 'api/billerAPI'
  import { addAppointment, queryAppointment } from 'api/appointmentAPI'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
          isLogin: store.getters.isLogin,
        pickerOptions: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        introduce: [
            'hair cut',
            'hair color',
            'hair wash & dry'
        ],
        times: [],
        searchForm: {
          serviceName: '',
          pageIndex: 1
        },
        formData: {
          serviceName: '',
          cost: '',
          duration: ''
        },
        application: {
          customerId: store.getters.userId,
          serviceId: '',
          date: '',
          time: '',
          location: '',
          status: '',
          message: ''
        },
        userInfo: {
          id: store.getters.userId,
          name: '',
          email: '',
          password: '',
          address: '',
          phone: '',
          comment: ''
        },
        billerInfo: {
          billerId: '',
          name: '',
          email: ''
        }
      }
    },
    created () {
    },
    methods: {
        handleLogin () {
            this.$router.push('/login')
        },
      async avaliableTime () {
        this.times = []
        let date = this.application.date
        const res = await queryAppointment({ date })
        if (res.success) {
          let applications = res.data.results
          let unavaliableTimes = new Set()
          let labels = [
            '9:00',
            '10:00',
            '11:00',
            '12:00',
            '13:00',
            '14:00',
            '15:00',
            '16:00'
          ]

          applications.forEach(item => {
            unavaliableTimes.add(parseInt(item.time))
          })

          for (var i = 1; i <= 8; i++) {
            if (!unavaliableTimes.has(i)) {
              this.times.push({
                value: i,
                label: labels[i - 1]
              })
            } else {
              this.times.push({
                value: i,
                label: labels[i - 1],
                disabled: true
              })
            }
          }
          console.log(this.times)
        } else {
          this.avaliableTime()
        }
      },
      async getUser () {
        const res = await getCustomer(store.getters.userId)
        if (res.success) {
          this.userInfo = res.data
        } else {
          this.getUser()
        }
      },
      async queryBiller () {
        var customerId = store.getters.userId
        const res = await queryBiller({ customerId })
        if (res.success) {
          this.billerInfo = res.data.results[0]
        } else {
          this.getUser()
        }
      },
        async showEdit (data) {
          this.dialogTitle = 'Book Service'
          this.application.serviceId = data.id
          this.application.location = this.userInfo.address
          this.application.status = 'pending'
          this.setFormData(this.userInfo)
          this.toggleDialog()
        },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        res = await addAppointment(this.application)
        if (res.success) {
          message = 'Apply Successful'
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
          message = 'Apply failed, please try again'
          this.$message.error(message)
        }
        this.times = []
        this.application = {
          customerId: store.getters.userId,
          serviceId: '',
          date: '',
          time: '',
          location: '',
          status: '',
          message: ''
        }
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getPageData () {
        const res = await queryService(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
        }
      },
        _checkForm () {
            if (!this.application.location) {
                this.$message.error('Please input location')
                return false
            }
            if (!this.application.date) {
                this.$message.error('Please input date')
                return false
            }
            if (!this.application.time) {
                this.$message.error('Please input time')
                return false
            }
            return true
        }
    },
    components: {
      AssetsPage
    },
    computed: {
      routes () {
        return routes.student
      }
    }
  }
</script>

<style lang="scss" scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .student {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
  .bannerImg {
    width: 100%;
    height: inherit;
    min-height: 200px;
    min-width: 200px;
  }
</style>
