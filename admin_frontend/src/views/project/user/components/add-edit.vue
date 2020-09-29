<template>
  <div class="add-edit">
    <div class="form-wrapper">
      <el-form
        ref="form"
        :model="formData"
        label-position="left"
        label-width="80px">
        <h1 class="title">账号信息</h1>
        <div class="account-wrapper">
          <el-form-item label="用户名">
            <el-input v-model="formData.user.userName" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="formData.user.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <!--          <el-form-item label="用户头像">-->
          <!--            <el-upload-->
          <!--              class="avatar-uploader"-->
          <!--              action=""-->
          <!--              :http-request="ossUpload"-->
          <!--              :show-file-list="false"-->
          <!--              :on-success="handleAvatarSuccess"-->
          <!--              :before-upload="beforeAvatarUpload">-->
          <!--              <img v-if="formData.user.avatar" :src="formData.user.avatar" class="avatar">-->
          <!--              <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
          <!--            </el-upload>-->
          <!--          </el-form-item>-->
          <el-form-item label="确认密码">
            <el-input v-model="formData.user.checkPassword" placeholder="请确认密码"></el-input>
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="formData.project.name" placeholder="请输入项目名称"></el-input>
          </el-form-item>
          <el-form-item label="项目地点">
            <el-input v-model="formData.project.place" placeholder="请输入项目地点"></el-input>
          </el-form-item>
          <el-form-item label="经度">
            <el-input v-model="formData.project.longitude" placeholder="请输入经度"></el-input>
          </el-form-item>
          <el-form-item label="纬度">
            <el-input v-model="formData.project.latitude" placeholder="请输入纬度"></el-input>
          </el-form-item>
        </div>
        <h1 class="title">功能权限</h1>
        <div class="permission-wrapper">
          <div class="permission" v-for="(permission, index) in formData.functions" :key="index">
            <el-checkbox v-model="permission.checked" @change="changePermission(permission)">
              <el-input :placeholder="permission.cnName" v-model="permission.alias" size="mini"></el-input>
            </el-checkbox>
            <div class="child-permission-wrapper" v-show="permission.children.length > 0 && permission.checked">
              <div class="child-permission" v-for="(childPermission, index) in permission.children" :key="index">
                <el-checkbox v-model="childPermission.checked">
                  <el-input :placeholder="childPermission.cnName" v-model="childPermission.alias"
                            size="mini"></el-input>
                </el-checkbox>
              </div>
            </div>
          </div>
        </div>
        <h1 class="title">问题类型</h1>
        <div class="questions-wrapper">
          <el-form-item
            v-for="(problem, index) in formData.problemTypes" :key="index" :label="'问题名称'+(index+1)"
            label-width="100px">
            <el-input v-model="problem.name" style="width: 300px" placeholder="请输入问题名称" size="mini"></el-input>
            <el-button @click="addProblemTypes" type="primary" class="btn-add" size="mini">添加</el-button>
            <el-button v-if="formData.problemTypes.length > 1" @click="deleteProblemTypes(problem)" type="danger"
                       size="mini">
              删除
            </el-button>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary" class="btn" @click="handleSubmit">保存</el-button>
          <el-button class="btn" @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { uploadMixin } from '@/common/js/mixin'
  import { getProject, addProject, editProject, getFunctions } from 'api/project'

  export default {
    name: 'addEdit',
    mixins: [uploadMixin],
    props: {
      type: {
        type: String,
        default: 'add'
      }
    },
    data () {
      return {
        formData: {
          project: {
            name: '',
            place: '',
            longitude: '',
            latitude: ''
          },
          user: {
            userName: '',
            password: '',
            checkPassword: '',
            avatar: ''
          },
          functions: [],
          problemTypes: []
        }
      }
    },
    created () {
      this.initFormData()
    },
    methods: {
      addProblemTypes () {
        this.formData.problemTypes.push({
          id: null,
          name: ''
        })
      },
      deleteProblemTypes (item) {
        const index = this.formData.problemTypes.indexOf(item)
        if (index !== -1) {
          this.formData.problemTypes.splice(index, 1)
        }
      },
      changePermission (data) {
        const checked = data.checked
        data.children.forEach(item => {
          item.checked = checked
        })
      },
      cancel () {
        this.$router.push('/project/user')
      },
      handleSubmit () {
        if (this.type === 'add') {
          // 新增
          this.addProject()
        } else {
          // 编辑
          this.editProject()
        }
      },
      async addProject () {
        if (!this._checkForm()) {
          return
        }
        const res = await addProject(this.formData)
        const self = this
        if (res.success) {
          this.$message({
            type: 'success',
            message: '新增成功',
            duration: 1000,
            onClose: () => {
              self.$router.push('/project/user')
            }
          })
        }
      },
      async editProject () {
        if (!this._checkForm()) {
          return
        }
        const res = await editProject(this.formData)
        const self = this
        if (res.success) {
          this.$message({
            type: 'success',
            message: '修改成功',
            duration: 1000,
            onClose: () => {
              self.$router.push('/project/user')
            }
          })
        }
      },
      async initFormData () {
        if (this.type === 'edit') {
          const projectId = this.$route.params.id
          const res = await getProject(projectId)
          if (res.success) {
            this.formData = res.data
            this.formData.user.checkPassword = res.data.user.password
          }
        } else {
          const res = await getFunctions()
          if (res.success) {
            this.formData.functions = res.data
          }
          this.addProblemTypes()
        }
      },
      _checkForm () {
        if (!this.formData.user.userName) {
          this.$message.error('请输入用户名称')
          return false
        }
        if (!this.formData.user.password) {
          this.$message.error('请输入密码')
          return false
        }
        if (this.formData.user.checkPassword !== this.formData.user.password) {
          this.$message.error('请确认两次密码是否一致')
          return false
        }
        if (!this.formData.project.name) {
          this.$message.error('请输入项目名称')
          return false
        }
        if (!this.formData.project.place) {
          this.$message.error('请输入项目地点')
          return false
        }
        if (!this.formData.project.longitude) {
          this.$message.error('请输入经度信息')
          return false
        }
        if (!this.formData.project.latitude) {
          this.$message.error('请输入纬度信息')
          return false
        }
        let flag = true
        for (let i = 0; i < this.formData.problemTypes.length; i++) {
          let problem = this.formData.problemTypes[i]
          if (!problem.name) {
            flag = false
            break
          }
        }
        if (!flag) {
          this.$message.error('问题名称不能为空')
          return false
        }
        return true
      }
    }
  }
</script>

<style lang="scss" scoped>
  .add-edit {
    position: relative;

    .form-wrapper {
      padding: 20px;

      .title {
        margin: 20px 0;
        font-size: 18px;
        font-weight: 700;

        &:first-child {
          margin-top: 0;
        }
      }

      .account-wrapper {
        width: 520px;
      }

      .permission-wrapper {
        .permission {
          margin-bottom: 10px;

          .child-permission-wrapper {
            padding: 0 0 0 25px;

            .child-permission {
              display: inline-block;
              margin: 10px 10px 0 0;
            }
          }
        }
      }

      .questions-wrapper {
        .btn-add {
          margin-left: 10px;
        }
      }

      .btn {
        width: 100px;
      }
    }
  }
</style>
